package com.justforfun.app.api.users.service.users;

import com.justforfun.app.api.users.data.user.AlbumServiceClient;
import com.justforfun.app.api.users.data.user.UserEntity;
import com.justforfun.app.api.users.data.user.UsersRepository;
import com.justforfun.app.api.users.mapper.CustomMapper;
import com.justforfun.app.api.users.shared.UserDTO;
import com.justforfun.app.api.users.ui.model.AlbumResponseModel;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    AlbumServiceClient albumServiceClient; //RestTemplate restTemplate;
    Environment environment;

    Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    CustomMapper customMapper;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            AlbumServiceClient albumServiceClient, //RestTemplate restTemplate
                            Environment environment)
    {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.albumServiceClient = albumServiceClient; //this.restTemplate = restTemplate;
        this.environment = environment;
    }

    // to create a user
    @Override
    public UserDTO createUser(UserDTO userDetails) {
        // generate random unique id, and encrypt password
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        usersRepository.save(userEntity);
        UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);
        return returnValue;
    }

    // to create a user via manual mapping
    @Override
    public UserDTO createUserV2(UserDTO userDetails) {
        // generate random unique id, and encrypt password
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        UserEntity userEntity = customMapper.toUserEntity(userDetails);
        usersRepository.save(userEntity);
        UserDTO returnValue = customMapper.toUserDTO(userEntity);
        return returnValue;
    }

    // to create random users by number input
    @Override
    public List<UserDTO> createRandomUser(int numberOfUsers) {
        // create a list for created random users
        List<UserDTO> createdRandomUsers = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // create user, save to database, and add into list
        for (int i = 0; i < numberOfUsers; i++) {
            UserDTO userDetails = new UserDTO();
            userDetails.setUserId(UUID.randomUUID().toString());
            userDetails.setFirstName("User");
            userDetails.setLastName("Number " + (i + 1));
            userDetails.setEmail("user" + (i + 1) + "@test.com");
            userDetails.setPassword("1234567890");
            userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
            UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
            usersRepository.save(userEntity);
            UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);
            createdRandomUsers.add(returnValue);
        }
        return createdRandomUsers;
    }

    // to create users by number input via manual mapping
    @Override
    public List<UserDTO> createRandomUserV2(int numberOfUsers) {
        // create a list for created random users
        List<UserDTO> createdRandomUsers = new ArrayList<>();
        // create user, save to database, and add into list
        for (int i = 0; i < numberOfUsers; i++) {
            UserDTO userDetails = new UserDTO();
            userDetails.setUserId(UUID.randomUUID().toString());
            userDetails.setFirstName("User");
            userDetails.setLastName("Number " + (i + 1));
            userDetails.setEmail("user" + (i + 1) + "@test.com");
            userDetails.setPassword("1234567890");
            userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
            UserEntity userEntity = customMapper.toUserEntity(userDetails);
            usersRepository.save(userEntity);
            UserDTO userReturnValue = customMapper.toUserDTO(userEntity);
            createdRandomUsers.add(userReturnValue);
        }
        return createdRandomUsers;
    }

    // get user by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);
        if(userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }

    // get user by email
    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDTO.class);
    }

    // get user by user id
    @Override
    public UserDTO getUserByUserId(String userId) {

        // using restTemplate
        /*
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");
        UserDTO userDto = new ModelMapper().map(userEntity, UserDTO.class);
        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);

        // RestTemplate object is declared in the main file
        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate
                .exchange(albumsUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AlbumResponseModel>>() {

                        });

        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
        userDto.setAlbums(albumsList);
        return userDto;
         */

        // using feign client via AlbumServiceClient, this to see the communication between microservices
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");
        UserDTO userDto = new ModelMapper().map(userEntity, UserDTO.class);
        List<AlbumResponseModel> albumsList = albumServiceClient.getAlbums(userId);
        userDto.setAlbums(albumsList);
        return userDto;

    }

    // get user data details by user id
    @Override
    public UserDTO getUserDataByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);
        UserDTO userDTO = customMapper.toUserDTO(userEntity);
        return userDTO;
    }

    // delete user by number input
    @Transactional
    @Override
    public void deleteUsersByAmount(int numberOfUsers) {
        usersRepository.deleteUsersByAmount(numberOfUsers);
    }

    // get all user id in a list
    @Override
    public List<String> getAllUserId() {
        List<String> listOfUserIds = usersRepository.findAllUserIds();
        return listOfUserIds;
    }

}
















// not working
//        @Autowired
//        MeterRegistry meterRegistry;
//        // Start timer
//        Timer.Sample sample = Timer.start(meterRegistry);
//        // Stop the timer and record the duration
//        long durationInMs = sample.stop(Timer.builder("repository.findUserDetailsByUserId")
//                .description("Time taken to execute findUserDetailsByUserId method in UsersRepository")
//                .register(meterRegistry));
//
//        // Log the duration
//        logger.info("findUserDetailsByUserId executed in " + durationInMs + " ms");

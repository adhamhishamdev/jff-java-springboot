package com.justforfun.app.api.users.ui.controllers;

import com.justforfun.app.api.users.mapper.CustomMapper;
import com.justforfun.app.api.users.service.users.UsersService;
import com.justforfun.app.api.users.shared.UserDTO;
import com.justforfun.app.api.users.ui.model.user.CreateUserRequestModel;
import com.justforfun.app.api.users.ui.model.user.CreateUserResponseModel;
import com.justforfun.app.api.users.ui.model.user.UserInfoResponseModel;
import com.justforfun.app.api.users.ui.model.user.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    UsersService usersService;

    @Autowired
    CustomMapper customMapper;

    @GetMapping("/status/check")
    public String status() {
        return "Get status API users is working on port " + env.getProperty("local.server.port");
    }

    // to create a user, or to sign up a user
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDto = modelMapper.map(userDetails, UserDTO.class);
        UserDTO createdUser = usersService.createUser(userDto);
        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    // to create a user, or to sign up a user via manual mapping
    @PostMapping("/signup/v2")
    public ResponseEntity<CreateUserResponseModel> createUserV2(@RequestBody CreateUserRequestModel userDetails) {
        UserDTO userDto = customMapper.toUserDTO(userDetails);
        UserDTO createdUser = usersService.createUserV2(userDto);
        CreateUserResponseModel responseModel = customMapper.toCreateUserResponseModel(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    // to generate users based on number input
    @PostMapping("/generate/user/{numberOfUsers}")
    public ResponseEntity<List<CreateUserResponseModel>> generateUsers(@PathVariable int numberOfUsers) {
        // list of generated users based on CreateUserResponseModel
        List<CreateUserResponseModel> generatedUsers = new ArrayList<>();
        // list of random created users
        List<UserDTO> createdRandomUsers = usersService.createRandomUser(numberOfUsers);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (int i = 0; i < numberOfUsers; i++) {
            CreateUserResponseModel returnValue = modelMapper.map(createdRandomUsers.get(i), CreateUserResponseModel.class);
            generatedUsers.add(returnValue);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedUsers);
    }

    // to generate users based on number input via manual mapping
    @PostMapping("/generate/user/v2/{numberOfUsers}")
    public ResponseEntity<List<CreateUserResponseModel>> generateUsersV2(@PathVariable int numberOfUsers) {
        // list of generated users based on CreateUserResponseModel
        List<CreateUserResponseModel> generatedUsers = new ArrayList<>();
        // list of random created users
        List<UserDTO> createdRandomUsers = usersService.createRandomUserV2(numberOfUsers);
        for (int i = 0; i < numberOfUsers; i++) {
            CreateUserResponseModel responseModel = customMapper.toCreateUserResponseModel(createdRandomUsers.get(i));
            generatedUsers.add(responseModel);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedUsers);
    }

    // to get user, or to sign in user
    @GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
        UserDTO userDto = usersService.getUserByUserId(userId);
        //UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
        UserResponseModel returnValue = customMapper.toUserResponseModel(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // get user info with its credit cards list and transactions list, as defined in the entity
    @GetMapping("/{userId}/data")
    public ResponseEntity<UserInfoResponseModel> getUserData(@PathVariable String userId) {
        UserDTO userDTO = usersService.getUserDataByUserId(userId);
        UserInfoResponseModel userData = customMapper.toUserInfoModel(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }

    // delete users in the database based on number input
    @DeleteMapping("/delete/user/rows/{numberOfUsers}")
    public ResponseEntity<Void> deleteUsersByAmount(@PathVariable int numberOfUsers) {
        usersService.deleteUsersByAmount(numberOfUsers);
        return ResponseEntity.noContent().build();
    }
}

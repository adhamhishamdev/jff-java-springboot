package com.justforfun.app.api.users.service.users;

import com.justforfun.app.api.users.data.user.UserEntity;
import com.justforfun.app.api.users.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {
    UserDTO createUser(UserDTO userDetails);
    UserDTO createUserV2(UserDTO userDetails);
    List<UserDTO> createRandomUser(int numberOfUsers);
    List<UserDTO> createRandomUserV2(int numberOfUsers);
    UserDTO getUserDetailsByEmail(String email);
    UserDTO getUserByUserId(String userId);
    UserDTO getUserDataByUserId(String userId);
    void deleteUsersByAmount(int numberOfUsers);
    List<String> getAllUserId();
}

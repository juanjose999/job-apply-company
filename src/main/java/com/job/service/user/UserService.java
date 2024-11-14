package com.job.service.user;
import com.job.service.dto.user.LoginFormUser;
import com.job.service.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> allUsers();
    UserResponseDto findUserByEmail(String email);
    UserResponseDto saveUser(LoginFormUser user);
    UserResponseDto updateUser(Long idUserToUpdate, LoginFormUser user);
    Boolean deleteUserById(Long idUser);
}

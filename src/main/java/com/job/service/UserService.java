package com.job.service;

import com.job.entities.MyUser;
import com.job.service.dto.LoginFormUser;
import com.job.service.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDto> allUsers();
    UserResponseDto findUserByEmail(String email);
    UserResponseDto saveUser(LoginFormUser user);
    UserResponseDto updateUser(Long idUserToUpdate, LoginFormUser user);
    Boolean deleteUserById(Long idUser);
}

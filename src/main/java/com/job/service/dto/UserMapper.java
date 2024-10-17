package com.job.service.dto;

import com.job.entities.MyUser;

public class UserMapper {
    public static MyUser loginFormToEntity(LoginFormUser loginFormUser){
        return MyUser.builder()
                .user_name(loginFormUser.userName())
                .password(loginFormUser.password())
                .email(loginFormUser.email())
                .build();
    }

    public static UserResponseDto entityToResponseDto(MyUser myUser){
        return UserResponseDto.builder()
                .userName(myUser.getUser_name())
                .email(myUser.getEmail())
                .build();
    }
}

package com.job.entities.user.dto;

import com.job.entities.user.MyUser;

public class MyUserMapper {

    public static MyUser UserDtoToUser(MyUserDto userDto) {
        return MyUser.builder()
                .first_name(userDto.first_name())
                .last_name(userDto.last_name())
                .email(userDto.email())
                .password(userDto.password())
                .build();
    }

    public static MyUserResponseDto UserToUserDto(MyUser user) {
        return MyUserResponseDto.builder()
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .build();
    }

}

package com.job.service.myuser;

import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;

import java.util.List;

public interface IMyUserService {
    List<MyUserResponseDto> allUser();
    MyUserResponseDto findUserByEmail(String email) throws MyUserNotFoundException;
    MyUserResponseDto saveUser(MyUserDto userDto);
    MyUserResponseDto updateUser(FormUpdateUser formUpdateUser) throws MyUserNotFoundException;
    boolean deleteUser(String email) throws MyUserNotFoundException;
}

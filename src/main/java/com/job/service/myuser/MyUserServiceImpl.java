package com.job.service.myuser;

import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;
import com.job.repository.myuser.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService {

    private final IMyUserRepository myUserRepository;

    @Override
    public List<MyUserResponseDto> allUser() {
        return myUserRepository.allUser().stream()
                .map(MyUserMapper::UserToUserDto)
                .toList();
    }

    @Override
    public MyUserResponseDto findUserByEmail(String email) throws MyUserNotFoundException{
        return myUserRepository.findUserByEmail(email).map(MyUserMapper::UserToUserDto)
                .orElseThrow(()->new MyUserNotFoundException("User not found with email : " + email ));
    }

    @Override
    public MyUserResponseDto saveUser(MyUserDto userDto) {
        return MyUserMapper.UserToUserDto(
                myUserRepository.saveUser(MyUserMapper.UserDtoToUser(userDto))
        );
    }

    @Override
    public MyUserResponseDto updateUser(FormUpdateUser formUpdateUser) throws MyUserNotFoundException {
        return MyUserMapper.UserToUserDto(
                myUserRepository.updateUser(formUpdateUser.email(), MyUserMapper.UserDtoToUser(formUpdateUser.userDto()))
                        .orElseThrow(()->new MyUserNotFoundException("User not found with email: " + formUpdateUser.email() ))
        );
    }

    @Override
    public boolean deleteUser(String email) throws MyUserNotFoundException {
        if(myUserRepository.deleteUser(email)){
            return true;
        }
        throw new MyUserNotFoundException("User not found with email : " + email);
    }
}

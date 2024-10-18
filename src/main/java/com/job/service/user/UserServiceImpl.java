package com.job.service.user;

import com.job.repository.user.UserRepository;
import com.job.service.dto.user.LoginFormUser;
import com.job.service.dto.user.UserMapper;
import com.job.service.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<UserResponseDto> allUsers() {
        return userRepository.allUsers().stream()
                .map(UserMapper::entityToResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        return UserMapper.entityToResponseDto(
                userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponseDto saveUser(LoginFormUser user) {
        return UserMapper.entityToResponseDto(
                userRepository.saveUser(
                        UserMapper.loginFormToEntity(user)
                ));
    }

    @Override
    public UserResponseDto updateUser(Long idUserToUpdate, LoginFormUser user) {
        return UserMapper.entityToResponseDto(
                userRepository.updateUser(idUserToUpdate,
                        UserMapper.loginFormToEntity(user))
        );
    }

    @Override
    public Boolean deleteUserById(Long idUser) {
        return userRepository.deleteUserById(idUser);
    }
}

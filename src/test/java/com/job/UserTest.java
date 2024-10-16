package com.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void shouldSavedUserReturnUser(){
        //cual es elcontenido del contrado
        MyUser user = new MyUser(1,"juan","juan@gmail.com","23123");
        UserRequest userRequest = new UserRequest("juan","juan@gmail.com","23123");
        //firmo el contrato
        MyUser userSavedInRepository = userRepository.saveUser(user);
        UserResponseDto userResponseDto = userService.saveUser(userRequest);
        //cumplio
        assertThat(userSavedInRepository).isNotNull();
        assertThat(userSavedInRepository.getId()).isEqualTo(1);
        assertThat(userSavedInRepository.getEmail).isEqualTo("juan@gmail.com","23123");
        assertThat(userSavedInRepository.getName).isEqualTo("juan");

        assertThat(userSavedInRepository.getId()).isEqualTo(1);
        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getEmail()).isEqualTo("juan@gmail.com","23123");
        assertThat(userResponseDto.getPassword()).isEqualTo("23123");

    }

}

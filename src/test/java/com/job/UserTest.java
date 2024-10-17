package com.job;

import com.job.entities.MyUser;
import com.job.repository.UserRepository;
import com.job.repository.UserRepositoryImpl;
import com.job.service.UserService;
import com.job.service.UserServiceImpl;
import com.job.service.dto.LoginFormUser;
import com.job.service.dto.UserMapper;
import com.job.service.dto.UserResponseDto;
import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp(){
        reset(userRepository,userRepository);
    }

    @Test
    public void shouldSaveUserAndReturnUserResponseDto(){
        //cual es elcontenido del contrado
        MyUser user = new MyUser(1L,"juan","23123","juan@gmail.com");
        LoginFormUser userRequest = new LoginFormUser("juan","23123","juan@gmail.com");

        //firmo el contrato
        when(userRepository.saveUser(any(MyUser.class))).thenReturn(user);
        MyUser userSavedInRepository = userRepository.saveUser(user);
        UserResponseDto userResponseDto = userService.saveUser(userRequest);
        //cumplio
        assertThat(userSavedInRepository).isNotNull();
        assertThat(userSavedInRepository.getId()).isEqualTo(1L);
        assertThat(userSavedInRepository.getEmail()).isEqualTo("juan@gmail.com");
        assertThat(userSavedInRepository.getUser_name()).isEqualTo("juan");
        assertThat(userSavedInRepository.getPassword()).isEqualTo("23123");

        assertThat(userResponseDto).isNotNull();
        assertThat(userSavedInRepository.getId()).isEqualTo(1L);
        assertThat(userResponseDto.email()).isEqualTo("juan@gmail.com");
        assertThat(userResponseDto.userName()).isEqualTo("juan");

    }

    @Test
    public void shouldListUserAndReturnUserResponseDto(){
        //que me promete usted señor test, una lista de MyUser
        MyUser user1 = new MyUser(1L,"Juan Jose sierr","23123","juan@gmail.com");
        MyUser user2 = new MyUser(2L,"Maria ordaz","5959pp9","maria@gmail.com");
        MyUser user3 = new MyUser(3L,"Diego Jaimes","dd23","diego@gmail.com");

        List<MyUser> userList = new ArrayList<>(Arrays.asList(user1,user2,user3));

        List<UserResponseDto> userResponseDtoList = userList.stream()
                .map(UserMapper::entityToResponseDto)
                .toList();
        //haga lo qque me prometio señor test
        when(userRepository.allUsers()).thenReturn(userList);
        List<MyUser> listRepository = userRepository.allUsers();
        List<UserResponseDto> userResponseDtos = userService.allUsers();
        //voy a comprobar si lo que hizo esta bien
        assertThat(listRepository).isNotNull();
        assertThat(listRepository.size()).isEqualTo(3);
        assertThat(listRepository.get(2).getEmail()).isEqualTo("diego@gmail.com");
        assertThat(listRepository.get(1).getPassword()).isEqualTo("5959pp9");

        assertThat(userResponseDtos).isNotNull();
        assertThat(userResponseDtos.size()).isEqualTo(3);
        assertThat(userResponseDtos.get(1).userName()).isEqualTo("Maria ordaz");
        assertThat(userResponseDtos.get(1).email()).isEqualTo("maria@gmail.com");
    }

    @Test
    public void shouldFindUserByEmailReturnUserResponseDto(){
        //que me tiene que hacer usted en el contrato es esto
        MyUser user = new MyUser(3L,"Diego Jaimes","dd23","diego@gmail.com");
        //userRepository.saveUser(user);
        // haga el contrato
        when(userRepository.findUserByEmail("diego@gmail.com")).thenReturn(Optional.of(user));
        Optional<MyUser> userRepo = userRepository.findUserByEmail("diego@gmail.com");
        UserResponseDto userResponseDto = userService.findUserByEmail("diego@gmail.com");
        //quiero validar si lo que hixo esta bien o mal
        assertThat(userRepo).isNotNull();
        assertThat(userRepo.get().getEmail()).isEqualTo("diego@gmail.com");
        assertThat(userRepo.get().getUser_name()).isEqualTo("Diego Jaimes");
        assertThat(userRepo.get().getPassword()).isEqualTo("dd23");

        assertThat(userResponseDto).isNotNull();
        assertThat(userRepo.get().getId()).isEqualTo(3L);
        assertThat(userRepo.get().getEmail()).isEqualTo("diego@gmail.com");
        assertThat(userRepo.get().getUser_name()).isEqualTo("Diego Jaimes");
    }

    @Test
    public void shouldUpdateUserAndReturnUserResponseDto(){
        //cual es el plano de lo que tengo que hace
        MyUser user = new MyUser(1L,"Diego Jaimes","dd23","diego@gmail.com");
        LoginFormUser userRequest = new LoginFormUser("Diego Jaimes Paez","23123xx","diegoja123@gmail.com");
        MyUser userEntity = UserMapper.loginFormToEntity(userRequest);
        userEntity.setId(1L);
        //voy hacer lo que dice en el metodo
        when(userRepository.updateUser(eq(1L),any(MyUser.class))).thenReturn(userEntity);
        MyUser userSavedInRepository = userRepository.updateUser(1L,userEntity);
        UserResponseDto updateService = userService.updateUser(1L, userRequest);
        //voy a confirma si me dieron lo que yo espaeraba
        assertThat(userSavedInRepository).isNotNull();
        assertThat(userSavedInRepository.getId()).isEqualTo(1L);
        assertThat(userSavedInRepository.getEmail()).isEqualTo("diegoja123@gmail.com");
        assertThat(userSavedInRepository.getUser_name()).isEqualTo("Diego Jaimes Paez");
        assertThat(userSavedInRepository.getPassword()).isEqualTo("23123xx");

        assertThat(updateService).isNotNull();
        assertThat(updateService.email()).isEqualTo("diegoja123@gmail.com");
        assertThat(updateService.userName()).isEqualTo("Diego Jaimes Paez");
    }

    @Test
    public void shouldUpdateUserAndIdNotFound(){
        when(userRepository.updateUser(eq(100L), any(MyUser.class)))
                .thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(100L, new LoginFormUser("Test User", "password123", "testuser@gmail.com"));
        } );
    }

    @Test
    public void shouldDeleteUserReturnBoolean(){

        when(userRepository.deleteUserById(1L)).thenReturn(true);

        boolean resultRepositoryOk = userRepository.deleteUserById(1L);
        boolean resultServiceOk = userService.deleteUserById(1L);

        assertThat(resultRepositoryOk).isTrue();
        assertThat(resultServiceOk).isTrue();

        when(userService.deleteUserById(1L)).thenReturn(false);

        boolean resultRepository = userRepository.deleteUserById(1L);
        boolean resultService = userService.deleteUserById(1L);

        assertThat(resultRepository).isFalse();
        assertThat(resultService).isFalse();
    }

}

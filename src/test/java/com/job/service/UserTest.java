package com.job.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.exceptions.MyUserNotFoundException;
import com.job.repository.myuser.MyUserRepositoryImpl;
import com.job.service.myuser.MyUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private MyUserRepositoryImpl userRepository;

    @InjectMocks
    private MyUserServiceImpl userService;

    @Test
    public void should_save_userDto_return_userResponseDto() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Gson gson = new Gson();
        FileReader fileReader = new FileReader("src/test/java/resource/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(fileReader, MyUserDto.class);
        MyUser myUser = MyUserMapper.UserDtoToUser(userDto);

        when(userRepository.saveUser(any(MyUser.class))).thenReturn(myUser);
        MyUserResponseDto userResponse = userService.saveUser(userDto);

        assertNotNull(userResponse);
        assertEquals("carlos", userResponse.first_name());
        assertEquals("gomez", userResponse.last_name());
        assertEquals("carlos@gmail.com", userResponse.email());

    }

    @Test
    public void should_findUserByEmail_return_userResponseDto() throws IOException, MyUserNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader fileReader = new FileReader("src/test/java/resource/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(fileReader, MyUserDto.class);
        MyUser myUser = MyUserMapper.UserDtoToUser(userDto);
        String email = userDto.email();

        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.of(myUser));
        MyUserResponseDto userResponseDto = userService.findUserByEmail(email);

        assertNotNull(userResponseDto);
        assertEquals("carlos@gmail.com", userResponseDto.email());
        assertEquals("carlos", userResponseDto.first_name());
    }

    @Test
    public void should_findUserByEmailNotFound_return_userException() throws MyUserNotFoundException{
        String emailNotExist = "noexistt@gmail.com";

        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.empty());
        MyUserNotFoundException exception = assertThrows(
                MyUserNotFoundException.class,
                () -> userService.findUserByEmail(emailNotExist)
        );

        assertEquals(MyUserNotFoundException.class, exception.getClass());
        assertEquals("User not found with email : " + emailNotExist, exception.getMessage());
    }


    @Test
    public void should_updateUser_withUserDto_return_userResponseDto() throws IOException, MyUserNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        File reader = new File("src/test/java/resource/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(reader, MyUserDto.class);
        MyUser updateIsOk = MyUser.builder()
                .first_name("juan jose")
                .last_name("sierra")
                .email("carlos@gmail.com")
                .password("000")
                .build();
        FormUpdateUser formUpdateUser = FormUpdateUser.builder()
                .emailFindUser(userDto.email())
                .first_name("juan jose")
                .last_name("sierra")
                .email("carlos@gmail.com")
                .password("000")
                .build();

        when(userRepository.updateUser(any(String.class), any(MyUser.class))).thenReturn(Optional.of(updateIsOk));
        MyUserResponseDto userResponseDto = userService.updateUser(formUpdateUser);

        assertNotNull(userResponseDto);
        assertEquals("juan jose", userResponseDto.first_name());
        assertEquals("sierra", userResponseDto.last_name());
        assertEquals("carlos@gmail.com", userResponseDto.email());
    }

    @Test
    public void should_deleteUserByEmail_return_true() throws IOException, MyUserNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerFile = new File("src/test/java/resource/UserDtoSingleFakeData.json");
        MyUserDto userDtoList = objectMapper.readValue(readerFile, MyUserDto.class);
        String email = userDtoList.email();

        when(userRepository.deleteUser(any(String.class))).thenReturn(true).thenAnswer(
                invocation -> {
                    String emailResult = invocation.getArgument(0);
                    assertEquals(emailResult, email);
                    return true;
                }
        );
        boolean deleteUserFromService = userService.deleteUser(email);
        assertTrue(deleteUserFromService);
    }

    @Test
    public void should_deleteUserWithEmailNotExist_return_userException() throws MyUserNotFoundException {
        String emailNotExist = "noexistt@gmail.com";
        when(userRepository.deleteUser(any(String.class))).thenReturn(false);
        assertThrows(MyUserNotFoundException.class, () -> userService.deleteUser(emailNotExist));
    }
}

package com.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.offer.dto.FormUpdateOffer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    private void should_save_userDto_return_userResponseDto(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> userDtoList = objectMapper.readValue(getClass().getResourceAsStream("./resource.json"), UserDto.class);
        UserDto userDto = userDtoList.get(0);
        MyUser user = UserMapper.UserDtoToUser(userDto);

        when(userRepository.saveUser(any(User.class))).thenReturn(user);
        UserResponseDto userResponse = userService.saveUser(userDto);

        assertNotNull(userResponse);
        assertEquals("juan jose", userResponse.firstName);
        assertEquals(userResponse("juan@gmail.com", userResponse.email()));
    }

    @Test
    private void should_findUserByEmail_return_userResponseDto(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> userDtoList = objectMapper.readValue(getClass().getResourceAsStream("./resource.json"), UserDto.class);
        UserDto singleUser = userDtoList.get(0);
        String email = userSingle.Email();

        when(userRepository.findUserByEmail(any(String.class))).thenReturn(UserMapper.userDtoToUser(userDto));
        UserResponseDto userResponseDto = userService.findUserByEmail(email);

        assertNotNull(userResponseDto);
        assertEquals("juan jose", userResponseDto.firstName());
    }

    @Test
    private void should_findUserByEmailNotFound_return_userExeption(){
        String emailNotExist = "noexistt@gmail.com";

        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.empty());
        Exception exception = userService.findUserByEmail(emailNotExist);

        assertTrow("User not found with email : " + emailNotExist, exception.getMessage());
    }

    @Test
    private void should_findAllUsers_return_listUserResponseDto(){
        ObjectMapper objectMapper = new ObjectMapper();

        List<UserDto> userDtoList = objectMapper.readValue(
                getClass().getResourceAsStream("./resource.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, UserDto.class));
        List<MyUser> userList = userDtoList.stream()
                        .map(UserMapper::userDtoToUser)
                                .toList();

        when(userRepository.findAllUser()).thenReturn(userList);
        List<UserDto> responseDtoService = userService.findAllUser();

        asserNotNull(responseDtoService);
        assertEquals(userDtoList, responseDtoService);

    }

    @Test
    private void should_updateUser_withUserDto_return_userResponseDto(){
        UserDto userDto = UserDto.Builder().build();
        MyUser user = UserMapper.userDtoToUser(userDto);
        FormUpdateUser formUpdateUser = FormUpdateUser.Builder()
                .firstName("kiko")
                .lastName("venalmadena")
                .email("123@gmail.com")
                .password("123")
                .build();

        when(userRepository.updateUser(any(String.class). any(MyUser.class))).thenReturn(user);
        UserResponseDto userResponseDto = userService.updateUser("juan@gmail.com", formUpdateUser);

        assertNotNull(userResponseDto);
        assertEquals("kiko", userResponseDto.firstName());
        assertEquals("123@gmail.com", userResponseDto.email());
        assertEquals("123", userResponseDto.password());

    }

    @Test
    private void should_deleteUserByEmail_return_true(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> userDtoList = objectMapper.readValue(
                getClass().getResourceAsStream("./resource.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, UserDto.class));

        UserDto singleUser = userDtoList.get(0);
        String email = singleUser.email();

        when(userRepository.deleteUserByEmail(any(String.class))).thenReturn(true);
        boolean deleteUserFromService = userService.deleteUserByEmail(email);

        assertTrue(deleteUserFromService);
    }

    @Test
    private void shoudl_deleteUserWithEmailNotExist_return_userException(){
        String emailNotExist = "noexistt@gmail.com";

        when(userRepository.deletUserByEmail(any(String.class))).thenReturn(Optional.empty());
        Exception exception = userService.deleteUserByEmail(emailNotExist);

        assertTrow("User not found with email: " + emailNotExist, exception.getMessage());
    }
}

package com.job.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.service.myuser.MyUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MyUserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyUserServiceImpl userService;


    @Test
    void should_create_user_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/resources/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value(user.getFirst_name()));

        Mockito.verify(userService).saveUser(Mockito.any(MyUserDto.class));
    }

    @Test
    void should_findUserByEmail_return_userDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/resources/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);
        String json = objectMapper.writeValueAsString(user);
        String email = user.getEmail();

        mockMvc.perform(get("v1/users/findByEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(user.getFirst_name()));

        Mockito.verify(userService).saveUser(Mockito.any(MyUserDto.class));
    }

    @Test
    void should_update_user_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/resources/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        FormUpdateUser formUpdateUser = FormUpdateUser.builder()
                .emailFindUser(userDto.email())
                .first_name("benek")
                .last_name("spring")
                .email("benek@gmail.com")
                .password("222xxx")
                .build();
        String jsonToUpdate = objectMapper.writeValueAsString(formUpdateUser);

        mockMvc.perform(put("v1/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(jsonToUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("benek"));
    }

    @Test
    void should_delete_user_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/resources/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);

        mockMvc.perform(delete("/v1/users/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDto.email()))
                .andExpect(status().isOk());
    }

    @Test
    void should_delete_userNotFound_return_404() throws Exception {
        String emailNotFound = "noexist@gmail.com";

        mockMvc.perform(delete("v1/users/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailNotFound))
                .andExpect(status().isNotFound());
    }

}

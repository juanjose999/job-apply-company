package com.job.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.apply.dto.FormResponseApplyOffer;
import com.job.entities.apply.dto.FormUserApplyOffer;
import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyMapper;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.OfferDto;
import com.job.entities.offer.dto.OfferMapper;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;
import com.job.service.myuser.MyUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
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
        File readSingleUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");

        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUserResponseDto userResponseDto = MyUserResponseDto.builder()
                .first_name(userDto.first_name())
                .last_name(userDto.last_name())
                .email(userDto.email())
                .build();
        MyUser user = MyUserMapper.UserDtoToUser(userDto);
        String json = objectMapper.writeValueAsString(userDto);

        Mockito.when(userService.saveUser(any(MyUserDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value(userDto.first_name()))
                .andExpect(jsonPath("$.last_name").value(userDto.last_name()))
                .andExpect(jsonPath("$.email").value(userDto.email()));
        Mockito.verify(userService).saveUser(any(MyUserDto.class));
    }

    @Test
    void should_findUserByEmail_return_userDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");

        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);
        MyUserResponseDto userResponseDto = MyUserResponseDto.builder()
                .first_name(userDto.first_name())
                .last_name(userDto.last_name())
                .email(userDto.email())
                .build();
        String json = objectMapper.writeValueAsString(user);
        String email = user.getEmail();

        Mockito.when(userService.findUserByEmail(email)).thenReturn(userResponseDto);

        mockMvc.perform(get("/v1/users/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(userDto.first_name()))
                .andExpect(jsonPath("$.last_name").value(userDto.last_name()))
                .andExpect(jsonPath("$.email").value(userDto.email()));

        Mockito.verify(userService).findUserByEmail(any(String.class));
    }

    @Test
    void should_update_user_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");

        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        FormUpdateUser formUpdateUser = FormUpdateUser.builder()
                .emailFindUser(userDto.email())
                .first_name("benek")
                .last_name("spring")
                .email("benek@gmail.com")
                .password("222xxx")
                .build();
        String jsonToUpdate = objectMapper.writeValueAsString(formUpdateUser);
        MyUserResponseDto userResponseDto = MyUserResponseDto.builder()
                .first_name(formUpdateUser.first_name())
                .last_name(formUpdateUser.last_name())
                .email(formUpdateUser.email())
                .build();

        Mockito.when(userService.updateUser(formUpdateUser)).thenReturn(userResponseDto);

        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("benek"))
                .andExpect(jsonPath("$.last_name").value("spring"))
                .andExpect(jsonPath("$.email").value("benek@gmail.com"));

    }

    @Test
    void should_userApplyOffer_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");
        File readSingleOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        File readSingleCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");

        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);

        OfferDto offerDto = objectMapper.readValue(readSingleOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(1l);

        CompanyDto companyDto = objectMapper.readValue(readSingleCompany, CompanyDto.class);
        Company company = CompanyMapper.CompanyDtoToCompany(companyDto);
        FormUserApplyOffer applyOffer = FormUserApplyOffer.builder()
                .emailUser(user.getEmail())
                .idOffer(1L)
                .build();
        String jsonRequest = objectMapper.writeValueAsString(applyOffer);

        Mockito.when(userService.userApplyOffer(applyOffer)).thenReturn(new FormResponseApplyOffer(offer.getTitle(), String.valueOf(LocalDateTime.now()), "PENDING"));
        mockMvc.perform(post("/v1/users/apply-offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void should_delete_user_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");

        MyUserDto userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);
        MyUserResponseDto userResponseDto = MyUserResponseDto.builder()
                .first_name(userDto.first_name())
                .last_name(userDto.last_name())
                .email(userDto.email())
                .build();
        String jsonEmailRequest = objectMapper.writeValueAsString(user.getEmail());

        Mockito.when(userService.deleteUser(jsonEmailRequest)).thenReturn(true);

        mockMvc.perform(delete("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content( jsonEmailRequest ))
                .andExpect(status().isOk());

        Mockito.verify(userService).deleteUser(jsonEmailRequest);
    }

    @Test
    void should_delete_userNotFound_return_exception() throws Exception, MyUserNotFoundException {
        String emailNotFound = "noexist@gmail.com";

        Mockito.when(userService.findUserByEmail(emailNotFound)).thenThrow(MyUserNotFoundException.class);
        mockMvc.perform(delete("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailNotFound))
                .andExpect(status().isNotFound());
        Mockito.verify(userService).deleteUser(emailNotFound);
    }

}

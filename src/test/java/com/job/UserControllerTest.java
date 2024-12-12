package com.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobify.user.controller.MyUserController;
import com.jobify.offer_user.dto.FormResponseApplyOffer;
import com.jobify.offer_user.dto.FormUserApplyOffer;
import com.jobify.company.entity.Company;
import com.jobify.company.dto.CompanyDto;
import com.jobify.company.dto.CompanyMapper;
import com.jobify.offer.entity.Offer;
import com.jobify.offer.dto.OfferDto;
import com.jobify.offer.dto.OfferMapper;
import com.jobify.user.entity.MyUser;
import com.jobify.user.dto.FormUpdateUser;
import com.jobify.user.dto.MyUserDto;
import com.jobify.user.dto.MyUserMapper;
import com.jobify.user.dto.MyUserResponseDto;
import com.jobify.shared.exception.exceptions.MyUserNotFoundException;
import com.jobify.user.service.MyUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

    private ObjectMapper objectMapper;
    private MyUserDto userDto;
    private MyUser user;
    private MyUserResponseDto userResponseDto;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        File readSingleUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");
        userDto = objectMapper.readValue(readSingleUser, MyUserDto.class);
        user = MyUserMapper.UserDtoToUser(userDto);
        userResponseDto = MyUserResponseDto.builder()
                .name(userDto.first_name() + userDto.last_name())
                .email(userDto.email())
                .counterAllApplications(0)
                .counterApplicationsRejected(0)
                .counterApplicationsToInterview(0)
                .build();
    }

    @Test
    void should_create_user_return_200() throws Exception {
        Mockito.when(userService.saveUser(userDto)).thenReturn(userResponseDto);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value(userDto.first_name()))
                .andExpect(jsonPath("$.last_name").value(userDto.last_name()))
                .andExpect(jsonPath("$.email").value(userDto.email()));

        Mockito.verify(userService).saveUser(userDto);
    }

    @Test
    void should_findUserByEmail_return_userDto() throws Exception {
        Mockito.when(userService.findUserByEmail(userDto.email())).thenReturn(userResponseDto);

        mockMvc.perform(get("/v1/users/email/{email}", userDto.email()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(userDto.first_name()))
                .andExpect(jsonPath("$.last_name").value(userDto.last_name()))
                .andExpect(jsonPath("$.email").value(userDto.email()));

        Mockito.verify(userService).findUserByEmail(userDto.email());
    }

    @Test
    void should_update_user_return_200() throws Exception {
        FormUpdateUser formUpdateUser = FormUpdateUser.builder()
                .emailFindUser(userDto.email())
                .first_name("benek")
                .last_name("spring")
                .email("benek@gmail.com")
                .password("222xxx")
                .build();

        MyUserResponseDto userResponseDto = MyUserResponseDto.builder()
                .name(formUpdateUser.first_name() + formUpdateUser.last_name())
                .email(formUpdateUser.email())
                .build();

        Mockito.when(userService.updateUserByEmail(formUpdateUser)).thenReturn(userResponseDto);
        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formUpdateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("benek"))
                .andExpect(jsonPath("$.last_name").value("spring"))
                .andExpect(jsonPath("$.email").value("benek@gmail.com"));

        Mockito.verify(userService).updateUserByEmail(formUpdateUser);
    }

    @Test
    void should_userApplyOffer_return_200() throws Exception {
        MyUser user = MyUserMapper.UserDtoToUser(userDto);

        File readSingleOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readSingleOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(1L);

        File readSingleCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readSingleCompany, CompanyDto.class);
        Company company = CompanyMapper.CompanyDtoToCompany(companyDto);

        FormUserApplyOffer applyOffer = FormUserApplyOffer.builder()
                .emailUser(user.getEmail())
                .idOffer(1L)
                .build();

        FormResponseApplyOffer responseApplyOffer = FormResponseApplyOffer.builder()
                .name_offer(offer.getTitle())
                .date_apply(String.valueOf(LocalDateTime.now()))
                .state("PENDING")
                .build();

        Mockito.when(userService.userApplyOffer(applyOffer)).thenReturn(new FormResponseApplyOffer(offer.getTitle(), String.valueOf(LocalDateTime.now()), "PENDING"));
        mockMvc.perform(post("/v1/users/apply-to-offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyOffer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name_offer").value(offer.getTitle()));

        Mockito.verify(userService).userApplyOffer(applyOffer);
    }

    @Test
    void should_delete_user_return_200() throws Exception {
        Mockito.when(userService.findUserByEmail(userDto.email())).thenReturn(userResponseDto);
        Mockito.when(userService.deleteUser(userDto.email())).thenReturn(true);
        mockMvc.perform(delete("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDto.email()))
                .andExpect(status().isOk());

        Mockito.verify(userService).deleteUser(userDto.email());
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

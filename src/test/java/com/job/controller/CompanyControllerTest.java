package com.job.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyMapper;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.exception.CompanyNotFoundException;
import com.job.service.company.CompanyServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyServiceImpl companyService;

    @Test
    void should_save_company_return_status_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        CompanyResponseDto companyResponseDto = CompanyResponseDto.builder()
                .fullName(companyDto.full_name())
                .email(companyDto.email())
                .build();

        Mockito.when(companyService.saveCompany(companyDto)).thenReturn(companyResponseDto);

        mockMvc.perform(post("/v1/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(companyDto.full_name()))
                .andExpect(jsonPath("$.email").value(companyDto.email()));

        Mockito.verify(companyService).saveCompany(companyDto);
    }

    @Test
    void should_findCompanyByEmail_then_return_companyResponse_and_status_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        CompanyResponseDto companyResponseDto = CompanyResponseDto.builder()
                .fullName(companyDto.full_name())
                .email(companyDto.email())
                .build();
        String email = "hola@gmail.com";

        Mockito.when(companyService.saveCompany(companyDto)).thenReturn(companyResponseDto);
        Mockito.when(companyService.findCompanyByEmail("hola@gmail.com")).thenReturn(companyResponseDto);

        mockMvc.perform(get("/v1/company/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(companyDto.full_name()))
                .andExpect(jsonPath("$.email").value(companyDto.email()));

        Mockito.verify(companyService).findCompanyByEmail(companyDto.email());
    }

    @Test
    void should_updateCompany_then_return_companyResponse_and_status_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        CompanyResponseDto companyResponseDto = CompanyResponseDto.builder()
                .fullName(companyDto.full_name())
                .email(companyDto.email())
                .build();
        FormUpdateCompany formUpdateCompany = FormUpdateCompany.builder()
                .emailFindCompany(companyDto.email())
                .full_name("vector pixel sas")
                .newEmailCompany("vectorpixel@gmail.com")
                .password("mmm")
                .build();
        CompanyResponseDto companyResponseDto1 = CompanyResponseDto.builder()
                .fullName("vector pixel sas")
                .email("vectorpixel@gmail.com")
                .build();

        Mockito.when(companyService.updateCompanyByEmail(formUpdateCompany)).thenReturn(companyResponseDto1);

        mockMvc.perform(put("/v1/company/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formUpdateCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(companyResponseDto1.fullName()))
                .andExpect(jsonPath("$.email").value(companyResponseDto1.email()));

        Mockito.verify(companyService).updateCompanyByEmail(formUpdateCompany);
    }

    @Test
    void should_deleteCompanyExist_then_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        CompanyResponseDto companyResponseDto1 = CompanyResponseDto.builder()
                .fullName(companyDto.full_name())
                .email(companyDto.email())
                .build();
        String email = "hola@gmail.com";

        Mockito.when(companyService.saveCompany(companyDto)).thenReturn(companyResponseDto1);
        Mockito.when(companyService.deleteCompanyByEmail(email)).thenReturn(true);

        mockMvc.perform(delete("/v1/company/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk());

        Mockito.verify(companyService).deleteCompanyByEmail(companyDto.email());
    }

    @Test
    void should_deleteCompany_not_exist_then_return_404() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String emailNotExist = "notexist@gmail.com";

        Mockito.when(companyService.deleteCompanyByEmail(emailNotExist)).thenReturn(false);

        mockMvc.perform(delete("/v1/company/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailNotExist))
                .andExpect(status().isNotFound());

        Mockito.verify(companyService).deleteCompanyByEmail(emailNotExist);
    }


}

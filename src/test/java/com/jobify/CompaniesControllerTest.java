package com.jobify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobify.company.controller.CompanyController;
import com.jobify.company.dto.CompanyDto;
import com.jobify.company.dto.CompanyMapper;
import com.jobify.company.dto.CompanyResponseDto;
import com.jobify.company.dto.FormUpdateCompany;
import com.jobify.shared.cloudinary.service.CloudinaryService;
import com.jobify.shared.exception.exceptions.CompanyNotFoundException;
import com.jobify.company.service.CompanyServiceImpl;
import com.jobify.offer.service.OfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(CompanyController.class)
public class CompaniesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyServiceImpl companyService;

    @MockBean
    private OfferServiceImpl offerService;

    @MockBean
    private CloudinaryService cloudinaryService;

    private ObjectMapper objectMapper;
    private CompanyDto companyDto;
    private CompanyResponseDto companyResponseDto;
    private List<CompanyDto> companyDtoList;
    private List<CompanyResponseDto> companyResponseDtos;

    @BeforeEach
    void setUp() throws Exception{
        objectMapper = new ObjectMapper();

        File readerAloneCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        companyDto = objectMapper.readValue(readerAloneCompany, CompanyDto.class);
        companyResponseDto = CompanyResponseDto.builder()
                .fullName(companyDto.full_name()).email(companyDto.email()).offerList(Collections.emptyList()).build();

        File readerCompaniesList = new File("src/test/java/resource/CompaniesListDtoFake.json");
        companyDtoList= objectMapper.readValue(readerCompaniesList, new TypeReference<List<CompanyDto>>() {});
        companyResponseDtos = companyDtoList.stream()
                .map(CompanyMapper::companyDtoToCompanyResponseDto)
                .toList();
    }

    @Test
    public void should_save_company_return_status_200() throws Exception {
        Mockito.when(companyService.saveCompany(companyDto)).thenReturn(companyResponseDto);
        mockMvc.perform(post("/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(companyResponseDto)))
                .andExpect(jsonPath("$.fullName").value(companyDto.full_name()))
                .andExpect(jsonPath("$.email").value(companyDto.email()));
        Mockito.verify(companyService).saveCompany(companyDto);
    }

    @Test
    void should_getAllCompanies_return_companiesList_status_200() throws Exception{
        Mockito.when(companyService.findAllCompany()).thenReturn(companyResponseDtos);
        mockMvc.perform(get("/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_findCompanyByEmail_then_return_companyResponse_and_status_200() throws Exception {
        String findEmail = companyDto.email();

        Mockito.when(companyService.findCompanyByEmail(findEmail)).thenReturn(companyResponseDto);
        mockMvc.perform(get("/v1/companies/email/{email}", findEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(findEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(companyDto.full_name()))
                .andExpect(jsonPath("$.email").value(companyDto.email()));

        Mockito.verify(companyService).findCompanyByEmail(companyDto.email());
    }

    @Test
    void should_updateCompany_then_return_companyResponse_and_status_200() throws Exception {

        FormUpdateCompany formUpdateCompany = FormUpdateCompany.builder()
                .emailFindCompany(companyDto.email())
                .full_name("vector pixel sas")
                .newEmailCompany("vectorpixel@gmail.com")
                .password("mmm123")
                .build();
        CompanyResponseDto companyResponseDtoUpdate = CompanyResponseDto.builder()
                .fullName("vector pixel sas")
                .email("vectorpixel@gmail.com")
                .build();

        Mockito.when(companyService.updateCompanyByEmail(formUpdateCompany)).thenReturn(companyResponseDtoUpdate);

        mockMvc.perform(put("/v1/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formUpdateCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(companyResponseDtoUpdate.fullName()))
                .andExpect(jsonPath("$.email").value(companyResponseDtoUpdate.email()));

        Mockito.verify(companyService).updateCompanyByEmail(formUpdateCompany);
    }

    @Test
    void should_updateCompany_not_exist_then_return_404() throws Exception {
        String emailNotExist = "notexist@gmail.com";
        String expectedErrorMessage = "Company not found";

        FormUpdateCompany formUpdateCompany = FormUpdateCompany.builder()
                .emailFindCompany(companyDto.email())
                .full_name("vector pixel sas")
                .newEmailCompany("notexist@gmail.com")
                .password("mmm123")
                .build();

        Mockito.when(companyService.updateCompanyByEmail(formUpdateCompany))
                .thenThrow(new CompanyNotFoundException(expectedErrorMessage));

        mockMvc.perform(put("/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formUpdateCompany)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }

    @Test
    void should_deleteCompanyExist_then_return_200() throws Exception {
        String emailToDelete = companyDto.email();

        Mockito.when(companyService.deleteCompanyByEmail(emailToDelete)).thenReturn(true);

        mockMvc.perform(delete("/v1/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailToDelete))
                .andExpect(status().isOk());

        Mockito.verify(companyService).deleteCompanyByEmail(companyDto.email());
    }

    @Test
    void should_deleteCompany_not_exist_then_return_404() throws Exception {
        String emailNotExist = "notexist@gmail.com";

        Mockito.when(companyService.deleteCompanyByEmail(emailNotExist)).thenReturn(false);

        mockMvc.perform(delete("/v1/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailNotExist))
                .andExpect(status().isNotFound());

        Mockito.verify(companyService).deleteCompanyByEmail(emailNotExist);
    }

}

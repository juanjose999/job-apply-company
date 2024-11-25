package com.job.service;

import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyMapper;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.exception.CompanyNotFoundException;
import com.job.repository.company.ICompanyRepositoryImpl;
import com.job.service.company.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyTest {

    @Mock
    private ICompanyRepositoryImpl companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    public void should_saveCompany_return_companyDto(){
        CompanyDto formSaveCompany = CompanyDto.builder()
                .full_name("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);
        company.setId(1L);

        when(companyRepository.saveCompany(any(Company.class))).thenReturn(company);
        Company companyResponse = companyRepository.saveCompany(company);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(company);
        assertEquals("hola company", company.getFull_name());
        assertEquals("hola@gmail.com", company.getEmail());

        assertNotNull(companyResponseDto);
        assertEquals("hola company", companyResponseDto.fullName());
        assertEquals("hola@gmail.com", companyResponseDto.email());
    }

    @Test
    public void should_findCompanyByEmail_return_companyDto() throws CompanyNotFoundException {
        String findEmail = "hola@gmail.com";
        CompanyDto formSaveCompany = CompanyDto.builder()
                .email(findEmail)
                .full_name("hola company")
                .password("123xxx")
                .build();
        Company companyObj = CompanyMapper.CompanyDtoToCompany(formSaveCompany);
        companyObj.setId(1L);

        when(companyRepository.saveCompany(any(Company.class))).thenReturn(companyObj);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(companyObj);
        assertNotNull(companyResponseDto);

        when(companyRepository.findCompanyByEmail(findEmail)).thenReturn(Optional.of(companyObj));
        Company company = companyRepository.findCompanyByEmail(findEmail).get();
        CompanyResponseDto companyResponseDtoByEmail = companyService.findCompanyByEmail(findEmail);

        assertNotNull(company);
        assertNotNull(companyResponseDtoByEmail);
        assertEquals("hola company",companyResponseDto.fullName());
        assertEquals("hola@gmail.com",companyResponseDto.email());
    }

    @Test
    public void should_findCompanyByEmailNotExist_return_CompanyException() throws CompanyNotFoundException {
        String findEmail = "hola@gmail.com";

        when(companyRepository.findCompanyByEmail(findEmail)).thenReturn(Optional.empty());
        Exception exceptionCompanyNotFound = assertThrows(CompanyNotFoundException.class, () -> companyService.findCompanyByEmail(findEmail));

        assertEquals("Company not found with email " + findEmail , exceptionCompanyNotFound.getMessage());
    }

    @Test
    public void should_updateCompanyByEmail_then_return_companyDto() throws CompanyNotFoundException {
        String findEmail = "hola@mail.com";

        Company company = Company.builder()
                .full_name("hola company")
                .email(findEmail)
                .password("123xxx")
                .build();

        Company companyUpdate = Company.builder()
                .full_name("bye company")
                .email("byeoy@gmail.com")
                .password("bye123")
                .build();

        FormUpdateCompany formUpdateCompany = FormUpdateCompany.builder()
                .emailFindCompany(findEmail)
                .full_name("bye company")
                .emailFindCompany("byeoy@gmail.com")
                .password("bye123")
                .build();

        when(companyRepository.findCompanyByEmail("byeoy@gmail.com")).thenReturn(Optional.of(companyUpdate));
        when(companyRepository.updateCompanyByEmail(any(String.class), any(Company.class))).thenReturn(companyUpdate);
        CompanyResponseDto companyResponseUpdateIsOk = companyService.updateCompanyByEmail(formUpdateCompany);


        assertNotNull(companyResponseUpdateIsOk);
        assertEquals("byeoy@gmail.com", companyResponseUpdateIsOk.email());
        assertEquals("bye company", companyResponseUpdateIsOk.fullName());
    }

    @Test
    public void should_deleteCompanyByEmail_return_true(){
        String findEmail = "hola@mail.com";
        CompanyDto formSaveCompany = CompanyDto.builder()
                .full_name("hola company")
                .email(findEmail)
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        when(companyRepository.saveCompany(any(Company.class))).thenReturn(company);
        Company companyObj = companyRepository.saveCompany(company);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(companyObj);
        assertNotNull(companyResponseDto);

        when(companyRepository.deleteCompanyByEmail(anyString())).thenReturn(true);
        boolean deleted = companyService.deleteCompanyByEmail(findEmail);

        assertTrue(deleted);

    }

}

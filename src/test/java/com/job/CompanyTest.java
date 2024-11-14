package com.job;

import com.job.entities.Company;
import com.job.entities.dto.CompanyDto;
import com.job.entities.dto.CompanyMapper;
import com.job.entities.dto.CompanyResponseDto;
import com.job.entities.dto.FormUpdateCompany;
import com.job.repository.ICompanyRepositoryImpl;
import com.job.service.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                .fullName("hola company")
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
    public void should_findCompanyByEmail_return_companyDto(){
        String findEmail = "hola@gmail.com";
        CompanyDto formSaveCompany = CompanyDto.builder()
                .email(findEmail)
                .fullName("hola company")
                .password("123xxx")
                .build();
        Company companyObj = CompanyMapper.CompanyDtoToCompany(formSaveCompany);
        companyObj.setId(1L);

        when(companyRepository.saveCompany(any(Company.class))).thenReturn(companyObj);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(companyObj);
        assertNotNull(companyResponseDto);

        when(companyRepository.findCompanyByEmail(findEmail)).thenReturn(companyObj);
        Company company = companyRepository.findCompanyByEmail(findEmail);
        CompanyResponseDto companyResponseDtoByEmail = companyService.findCompanyByEmail(findEmail);

        assertNotNull(company);
        assertNotNull(companyResponseDtoByEmail);
        assertEquals("hola company ",companyResponseDto.fullName());
        assertEquals("hola@gmail.com",companyResponseDto.email());
    }

    @Test
    public void should_updateCompanyByEmail_then_return_companyDto(){
        String findEmail = "hola@mail.com";
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email(findEmail)
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        FormUpdateCompany formUpdateCompany = FormUpdateCompany.builder()
                .email(findEmail)
                .companyDto(formSaveCompany)
                .build();

        CompanyDto companyUpdate = CompanyDto.builder()
                .email("byeoy@gmail.com")
                .fullName("bye company")
                .password("bye123")
                .build();

        when(companyRepository.saveCompany(any(Company.class))).thenReturn(company);
        Company companyObj = companyRepository.saveCompany(company);
        CompanyResponseDto companyResponseNotUpdate = companyService.saveCompany(formSaveCompany);

        assertNotNull(companyObj);
        assertEquals("hola@mail.com", companyObj.getEmail());
        assertEquals("hola company", companyObj.getFull_name());
        assertNotNull(companyResponseNotUpdate);
        assertEquals(findEmail, companyResponseNotUpdate.email());
        assertEquals("hola company", companyResponseNotUpdate.fullName());


        when(companyRepository.updateCompanyByEmail(anyString(), any(Company.class))).thenReturn(CompanyMapper.CompanyDtoToCompany(companyUpdate));
        Company companyUpdateIsOk = companyRepository.updateCompanyByEmail(findEmail, CompanyMapper.CompanyDtoToCompany(companyUpdate));
        FormUpdateCompany formUpdateCompanyToUpdate = FormUpdateCompany.builder()
                .email(findEmail)
                .companyDto(companyUpdate)
                .build();

        CompanyResponseDto companyResponseUpdateIsOk = companyService.updateCompanyByEmail(formUpdateCompany);

        assertNotNull(companyUpdateIsOk);
        assertEquals("byeoy@gmail.com",companyUpdateIsOk.getEmail());
        assertNotNull(companyResponseUpdateIsOk);
        assertEquals("byeoy@gmail.com", companyResponseUpdateIsOk.email());
        assertEquals("bye company", companyResponseUpdateIsOk.fullName());


    }

    @Test
    public void should_deleteCompanyByEmail_return_true(){
        String findEmail = "hola@mail.com";
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
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

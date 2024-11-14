package com.job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyTest {

    @Mock
    private CompanyRepositoryImpl companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    public void should_saveCompany_return_companyDto(){
        FormSaveCompany formSaveCompany = FormSaveCompany.builder().buil;

        when(companyRepository.saveCompany).thenReturn(Company.class);
        Company company = companyRepository.saveCompany(CompanyMapper.formToCompany(formSaveCompany));
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(company);
        assertNotNull(companyResponseDto);
        assertEquals("hola", companyResponseDto.getName());
        assertEquals("hola@mail.com", companyResponseDto.getEmail());
    }

    @Test
    public void should_findCompanyByEmail_return_companyDto(){
        String findEmail = "hola@mail.com";
        FormSaveCompany formSaveCompany = FormSaveCompany.builder().build;
        Company company = CompanyMapper.formSaveToCompany(formSaveCompany);

        when(companyRepository.saveCompany(company)).thenReturn(Company.class);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(company);
        assertNotNull(companyResponseDto);

        when(companyRepository.findByEmail(findEmail)).thenReturn(Company.class);
        Company companyByEmail = companyRepository.findByEmail(findEmail);
        CompanyResponseDto companyResponseDtoByEmail = companyService.findByEmail(findEmail);

        assertNotNull(companyByEmail);
        assertNotNull(companyResponseDtoByEmail);
        assertEquals("hola",companyResponseDto.name());
        assertEquals("hola@mail.com",companyResponseDto.email());
    }

    @Test
    public void should_updateCompanyByEmail_then_return_companyDto(){
        String findEmail = "hola@mail.com";
        FormSaveCompany formSaveCompany = FormSaveCompany.builder().build;
        Company company = CompanyMapper.formSaveToCompany(formSaveCompany);

        when(companyService.saveCompany(company)).thenReturn(Company.class);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(company);
        assertNotNull(companyResponseDto);

        FormUpdateCompany formUpdateCompany = new FormUpdateCompany();

        when(companyRepository.updateCompanyByEmail(findEmail)).thenReturn(Company.class);
        Company companyDtoUpdate = companyService.updateCompanyByEmail(findEmail, formUpdateCompany);

        assertNotNull(companyDtoUpdate);
        assertEquals("hola@mail.com",companyDtoUpdate.email());
        assertEquals("hola mundo",companyDtoUpdate.name());

    }

    @Test
    public void should_deleteCompanyByEmail_return_true(){
        String email = "hola@mail.com";
        FormSaveCompany formSaveCompany = FormSaveCompany.builder().build;
        Company company = CompanyMapper.formSaveToCompany(formSaveCompany);

        when(companyRepository.saveCompany(company)).thenReturn(Company.class);
        CompanyResponseDto companyResponseDto = companyService.saveCompany(formSaveCompany);

        assertNotNull(companyResponseDto);

        when(companyRepository.deleteCompanyByEmail(email)).thenReturn(true);

        assertNull(companyResponseDto);

    }

}

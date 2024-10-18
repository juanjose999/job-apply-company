package com.job;

import com.job.entities.Company;
import com.job.repository.company.CompanyRepositoryImpl;
import com.job.service.company.CompanyServiceImpl;
import com.job.service.dto.company.CompanyMapper;
import com.job.service.dto.company.CompanyResponseDto;
import com.job.service.dto.company.LoginFormCompany;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyTest {

    @Mock
    public CompanyRepositoryImpl companyRepository;

    @InjectMocks
    public CompanyServiceImpl companyService;

    @Test
    public void should_save_company_returnCompanyResponseDto(){
        LoginFormCompany loginFormCompany = LoginFormCompany.builder()
                .nameCompany("testers java")
                .email("testerjava@gmail.com")
                .password("123")
                .build();

        Company company = CompanyMapper.RequestToEntity(loginFormCompany);
        company.setId(1L);

        CompanyResponseDto companyResponseDto = CompanyMapper.EntityToRequest(company);

        when(companyRepository.saveCompany(any(Company.class))).thenReturn(company);
        Company companySaveRepository = companyRepository.saveCompany(company);
        CompanyResponseDto companyResponseService = companyService.saveCompany(loginFormCompany);


        assertThat(companySaveRepository).isNotNull();
        assertThat(companySaveRepository.getId()).isEqualTo(1L);
        assertThat(companySaveRepository.getEmail()).isEqualTo("testerjava@gmail.com");
        assertThat(companySaveRepository.getPassword()).isEqualTo("123");

        assertThat(companyResponseService).isNotNull();
        assertThat(companyResponseService).isInstanceOf(CompanyResponseDto.class);
        assertThat(companyResponseService.id()).isEqualTo(company.getId());
        assertThat(companyResponseService.email()).isEqualTo("testerjava@gmail.com");
    }

    @Test
    public void should_update_company_returnCompanyResponseDto(){

    }
}

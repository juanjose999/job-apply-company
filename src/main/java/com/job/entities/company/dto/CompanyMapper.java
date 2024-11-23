package com.job.entities.company.dto;

import com.job.entities.company.Company;

public class CompanyMapper {

    public static Company CompanyDtoToCompany(CompanyDto companyDto){
        return Company.builder()
                .full_name(companyDto.full_name())
                .email(companyDto.email())
                .password(companyDto.password())
                .build();
    }

    public static CompanyResponseDto CompanyToCompanyResponseDto(Company company){
        return CompanyResponseDto.builder()
                .fullName(company.getFull_name())
                .email(company.getEmail())
                .build();
    }

}

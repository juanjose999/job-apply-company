package com.job.service.dto.company;

import com.job.entities.Company;

import java.util.ArrayList;

public class CompanyMapper {

    public static Company RequestToEntity(LoginFormCompany loginFormCompany){
        return Company.builder()
                .name_company(loginFormCompany.nameCompany())
                .email(loginFormCompany.email())
                .password(loginFormCompany.password())
                .build();
    }

    public static CompanyResponseDto EntityToRequest(Company company){
        return CompanyResponseDto.builder()
                .id(company.getId())
                .nameCompany(company.getName_company())
                .email(company.getEmail())
                .offertList(new ArrayList<>())
                .build();
    }

}

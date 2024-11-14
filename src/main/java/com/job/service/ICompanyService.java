package com.job.service;

import com.job.entities.dto.CompanyDto;
import com.job.entities.dto.CompanyResponseDto;
import com.job.entities.dto.FormUpdateCompany;

import java.util.List;

public interface ICompanyService {
    List<CompanyResponseDto> findAllCompany();
    CompanyResponseDto saveCompany(CompanyDto companyDto);
    CompanyResponseDto findCompanyByEmail(String email);
    CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany);
    boolean deleteCompanyByEmail(String email);
}

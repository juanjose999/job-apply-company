package com.job.service;

import com.job.entities.dto.CompanyDto;
import com.job.entities.dto.CompanyResponseDto;
import com.job.entities.dto.FormUpdateCompany;
import com.job.exception.CompanyNotFoundException;

import java.util.List;

public interface ICompanyService {
    List<CompanyResponseDto> findAllCompany();
    CompanyResponseDto saveCompany(CompanyDto companyDto);
    CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException;
    CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException;
    boolean deleteCompanyByEmail(String email);
}

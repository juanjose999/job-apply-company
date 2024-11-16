package com.job.service.company;

import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.exception.CompanyNotFoundException;

import java.util.List;

public interface ICompanyService {
    List<CompanyResponseDto> findAllCompany();
    CompanyResponseDto saveCompany(CompanyDto companyDto);
    CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException;
    CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException;
    boolean deleteCompanyByEmail(String email);
}

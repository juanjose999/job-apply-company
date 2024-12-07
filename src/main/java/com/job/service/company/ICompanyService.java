package com.job.service.company;

import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.entities.offer_apply_user.dto.OffersWithApplicationsResponseDto;
import com.job.exception.exceptions.CompanyNotFoundException;
import io.vavr.control.Either;

import java.util.List;

public interface ICompanyService {
    List<CompanyResponseDto> findAllCompany();
    CompanyResponseDto saveCompany(CompanyDto companyDto);
    CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException;
    OffersWithApplicationsResponseDto findOffersWithApplicationsByEmailCompany(String emailCompany) throws CompanyNotFoundException;
    CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException;
    boolean deleteCompanyByEmail(String email);
}

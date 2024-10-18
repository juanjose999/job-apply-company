package com.job.service.company;

import com.job.entities.Company;
import com.job.service.dto.company.CompanyResponseDto;
import com.job.service.dto.company.LoginFormCompany;
import com.job.service.dto.offer.FormSaveOfferInsideCompany;
import com.job.service.dto.offer.OfferResponseDto;

import java.util.List;

public interface CompanyService {
    List<CompanyResponseDto> findAll();
    CompanyResponseDto findByEmail(String email);
    CompanyResponseDto saveCompany(LoginFormCompany loginFormCompany);
    OfferResponseDto saveOfferInsideCompany(FormSaveOfferInsideCompany formSaveOfferInsideCompany);
    CompanyResponseDto updateCompany(String email, LoginFormCompany loginFormCompany);
    Boolean deleteCompany(String email);
}

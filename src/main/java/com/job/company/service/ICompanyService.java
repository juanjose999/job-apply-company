package com.job.company.service;

import com.job.company.dto.CompanyDto;
import com.job.company.dto.CompanyResponseDto;
import com.job.company.dto.FormUpdateCompany;
import com.job.company.dto.UpdateStateOfferInsideCompany;
import com.job.offer_user.dto.OffersWithApplicationsResponseDto;
import com.job.shared.exception.exceptions.CompanyNotFoundException;
import com.job.shared.exception.exceptions.OfferNotFoundException;
import io.vavr.control.Either;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICompanyService {
    List<CompanyResponseDto> findAllCompany();
    CompanyResponseDto saveCompany(CompanyDto companyDto);
    Either<String, String> uploadImgProfileCompany(MultipartFile file, String emailCompany) throws CompanyNotFoundException;
    CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException;
    OffersWithApplicationsResponseDto findOffersWithApplicationsByEmailCompany(String emailCompany) throws CompanyNotFoundException;
    CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException;
    Either<String, String> updateStateOfferToRejectByEmailCompany(UpdateStateOfferInsideCompany updateStateOfferInsideCompany) throws CompanyNotFoundException, OfferNotFoundException;
    Either<String, String> updateStateOfferToInterviewedByEmailCompany(UpdateStateOfferInsideCompany updateStateOfferInsideCompany) throws CompanyNotFoundException, OfferNotFoundException;
    boolean deleteCompanyByEmail(String email);
}

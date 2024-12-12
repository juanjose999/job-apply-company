package com.jobify.company.service;

import com.jobify.company.dto.CompanyDto;
import com.jobify.company.dto.CompanyResponseDto;
import com.jobify.company.dto.FormUpdateCompany;
import com.jobify.company.dto.UpdateStateOfferInsideCompany;
import com.jobify.offer_user.dto.OffersWithApplicationsResponseDto;
import com.jobify.shared.exception.exceptions.CompanyNotFoundException;
import com.jobify.shared.exception.exceptions.OfferNotFoundException;
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

package com.jobify.company.dto;

import com.jobify.company.entity.Company;
import com.jobify.offer_user.dto.OfferSingleWithAllApplications;
import com.jobify.offer_user.repository.IApplyOfferJpa;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CompanyMapper {

    private static IApplyOfferJpa applyOfferJpa;

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

    public static CompanyResponseDto CompanyToCompanyResponseDto(Company company, List<OfferSingleWithAllApplications> offerSingleWithAllApplications){
        return CompanyResponseDto.builder()
                .fullName(company.getFull_name())
                .email(company.getEmail())
                .offerList(offerSingleWithAllApplications)
                .build();
    }

    public static CompanyResponseDto companyDtoToCompanyResponseDto(CompanyDto companyDto){
        return CompanyResponseDto.builder()
                .fullName(companyDto.full_name())
                .email(companyDto.email())
                .build();
    }

}

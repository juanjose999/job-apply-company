package com.job.entities.company.dto;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.OfferMapper;
import com.job.repository.apply.IApplyOfferJpa;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class CompanyMapper {

    private final IApplyOfferJpa applyOfferJpa;

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

    public static CompanyResponseDto companyDtoToCompanyResponseDto(CompanyDto companyDto){
        return CompanyResponseDto.builder()
                .fullName(companyDto.full_name())
                .email(companyDto.email())
                .build();
    }


    public static CompanyResponseDto CompanyToCompanyResponseDtoList(Company company){
        List<Offer> offers = company.getOffer();
        return CompanyResponseDto.builder()
                .fullName(company.getFull_name())
                .email(company.getEmail())
                .offerList(company.getOffer().stream()
                        .map(o -> OfferMapper.offerToOfferResponseDto(o))
                        .toList())
                .build();
    }

}

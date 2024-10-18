package com.job.service.dto.offer;

import com.job.entities.Offert;

public class OfferMapper {

    public static Offert OfferFormLoginToEntity (LoginFormOffer loginFormOffer){
        return Offert.builder()
                .title(loginFormOffer.title())
                .description(loginFormOffer.description())
                .skills(loginFormOffer.skills())
                .is_active(loginFormOffer.is_active())
                .date_created(loginFormOffer.date_created())
                .build();
    }

    public static LoginFormOffer EntityToLoginForm (Offert offert){
        return LoginFormOffer.builder()
                .title(offert.getTitle())
                .description(offert.getDescription())
                .skills(offert.getSkills())
                .is_active(offert.getIs_active())
                .date_created(offert.getDate_created())
                .build();
    }

    public static OfferResponseDto EntityToOfferResponseDto (Offert offert){
        return OfferResponseDto.builder()
                .title(offert.getTitle())
                .description(offert.getDescription())
                .skills(offert.getSkills())
                .is_active(offert.getIs_active())
                .date_created(offert.getDate_created())
                .companyList(offert.getCompanyList())
                .build();
    }

}

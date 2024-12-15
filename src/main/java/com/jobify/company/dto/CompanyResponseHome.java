package com.jobify.company.dto;

import com.jobify.offer.entity.Offer;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyResponseHome(
        String nameCompany,
        String linkImgProfile,
        String email,
        int totalOffers,
        int totalApply,
        int totalToInterview,
        List<Offer> offersList
) {
}

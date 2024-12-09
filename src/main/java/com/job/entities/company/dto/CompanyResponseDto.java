package com.job.entities.company.dto;

import com.job.entities.offer.dto.OfferResponseDto;
import com.job.entities.offer_apply_user.dto.OfferSingleWithAllApplications;
import com.job.entities.offer_apply_user.dto.OffersWithApplicationsResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyResponseDto(String fullName, String email, List<OfferSingleWithAllApplications> offerList) {
}

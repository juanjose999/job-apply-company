package com.job.company.dto;

import com.job.offer_user.dto.OfferSingleWithAllApplications;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyResponseDto(String fullName, String email, List<OfferSingleWithAllApplications> offerList) {
}

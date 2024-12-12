package com.jobify.company.dto;

import com.jobify.offer_user.dto.OfferSingleWithAllApplications;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyResponseDto(String fullName, String email, List<OfferSingleWithAllApplications> offerList) {
}

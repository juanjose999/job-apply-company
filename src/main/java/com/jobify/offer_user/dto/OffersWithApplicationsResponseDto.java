package com.jobify.offer_user.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record OffersWithApplicationsResponseDto(String nameCompany,
                                                List<OfferSingleWithAllApplications> offersWithAllApplications) {
}

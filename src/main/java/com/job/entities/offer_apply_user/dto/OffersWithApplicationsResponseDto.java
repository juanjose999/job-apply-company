package com.job.entities.offer_apply_user.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record OffersWithApplicationsResponseDto(String nameCompany,
                                                List<OfferSingleWithAllApplications> offerSingleWithAllApplications) {
}

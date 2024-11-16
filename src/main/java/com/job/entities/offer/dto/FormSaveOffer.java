package com.job.entities.offer.dto;

import lombok.Builder;

@Builder
public record FormSaveOffer(OfferDto offerDto, String emailCompany) {
}

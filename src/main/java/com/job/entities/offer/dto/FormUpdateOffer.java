package com.job.entities.offer.dto;

import lombok.Builder;

@Builder
public record FormUpdateOffer(Long idOffer, OfferDto offerDto, String emailCompany) {
}

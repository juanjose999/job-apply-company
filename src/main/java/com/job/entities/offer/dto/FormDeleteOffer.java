package com.job.entities.offer.dto;

import lombok.Builder;

@Builder
public record FormDeleteOffer(Long idOffer, String emailCompany) {
}

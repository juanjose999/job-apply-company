package com.job.offer.dto;

import lombok.Builder;

@Builder
public record FormDeleteOffer(Long idOffer, String emailCompany) {
}

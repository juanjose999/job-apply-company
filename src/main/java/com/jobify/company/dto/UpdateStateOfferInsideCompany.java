package com.jobify.company.dto;

import lombok.Builder;

@Builder
public record UpdateStateOfferInsideCompany( String emailCompany, Long idOffer, Long idApplyOffer) {
}
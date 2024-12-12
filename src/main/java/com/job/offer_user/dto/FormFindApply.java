package com.job.offer_user.dto;

import lombok.Builder;

@Builder
public record FormFindApply(String emailCompany, Long idOffer, Long idApply) {
}

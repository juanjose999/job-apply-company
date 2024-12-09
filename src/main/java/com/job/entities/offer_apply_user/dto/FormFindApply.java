package com.job.entities.offer_apply_user.dto;

import lombok.Builder;

@Builder
public record FormFindApply(String emailCompany, Long idOffer, Long idApply) {
}

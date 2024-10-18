package com.job.service.dto.offer;

import lombok.Builder;

@Builder
public record FormSaveOfferInsideCompany(
        String emailCompany,
        LoginFormOffer formOffer
) {
}

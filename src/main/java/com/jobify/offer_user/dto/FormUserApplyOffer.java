package com.jobify.offer_user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FormUserApplyOffer(
        @NotBlank(message = "Email user is required")
        String emailUser,
        @NotBlank(message = "id offer is required")
        Long idOffer
) {
}

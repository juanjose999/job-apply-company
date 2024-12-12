package com.jobify.offer.dto;

import jakarta.validation.constraints.NotEmpty;

public record OfferStatusUpdateForm(@NotEmpty(message = "Id offer to update cannot be empty")
                                    Long idOffer,
                                    @NotEmpty(message = "Email company to update cannot be empty")
                                    String emailCompany,
                                    @NotEmpty(message = "State in boolean cannot be empty")
                                    boolean state) {
}

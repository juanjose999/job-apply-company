package com.job.entities.offer.dto;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
public record FormSaveOffer(String title,  String description, String requirements, boolean active, String emailCompany) {
}

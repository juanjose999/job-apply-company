package com.job.entities.offer.dto;

import lombok.Builder;

@Builder
public record FormSaveOffer(String title, String description, String requirements, String date_created, boolean active, String emailCompany) {
}

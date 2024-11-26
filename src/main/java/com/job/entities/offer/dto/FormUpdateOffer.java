package com.job.entities.offer.dto;

import lombok.Builder;

@Builder
public record FormUpdateOffer(Long idOffer, String emailCompany, String title, String description, String requirements, String date_create, boolean active) {
}

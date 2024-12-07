package com.job.entities.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(Long id, String title, String description, String requirements, String date_create) {
}

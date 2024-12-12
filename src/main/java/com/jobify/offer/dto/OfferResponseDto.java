package com.jobify.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(Long id, String title, String description, String requirements, String date_create) {
}

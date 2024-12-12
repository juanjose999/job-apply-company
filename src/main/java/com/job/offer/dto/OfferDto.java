package com.job.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(String title, String description, String requirements, String date_create, boolean active ) {

}

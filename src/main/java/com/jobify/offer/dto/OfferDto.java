package com.jobify.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(String title, String description, String requirements, String date_create, boolean active ) {

}

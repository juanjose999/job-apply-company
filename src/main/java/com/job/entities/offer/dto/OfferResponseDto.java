package com.job.entities.offer.dto;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.apply.dto.FormResponseApplyOffer;
import lombok.Builder;

import java.util.List;

@Builder
public record OfferResponseDto(Long id, String title, String description, String requirements, String date_create) {
}

package com.job.entities.company.dto;

import com.job.entities.offer.dto.OfferResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyResponseDto(String fullName, String email, List<OfferResponseDto> offerList) {
}

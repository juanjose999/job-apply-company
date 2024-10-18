package com.job.service.dto.offer;

import com.job.entities.Company;
import lombok.Builder;

import java.util.List;
@Builder
public record OfferResponseDto(String title,
                               String description,
                               String skills,
                               String date_created,
                               Boolean is_active,
                               List<Company> companyList) {
}

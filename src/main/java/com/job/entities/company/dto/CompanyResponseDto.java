package com.job.entities.company.dto;

import lombok.Builder;

@Builder
public record CompanyResponseDto(String fullName, String email) {
}

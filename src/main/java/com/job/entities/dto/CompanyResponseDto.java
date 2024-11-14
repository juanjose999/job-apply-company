package com.job.entities.dto;

import lombok.Builder;

@Builder
public record CompanyResponseDto(String fullName, String email) {
}

package com.job.entities.company.dto;

import lombok.Builder;

@Builder
public record CompanyDto(String fullName, String email, String password) {
}

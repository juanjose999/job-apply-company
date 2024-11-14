package com.job.entities.dto;

import lombok.Builder;

@Builder
public record CompanyDto(String fullName, String email, String password) {
}

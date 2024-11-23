package com.job.entities.company.dto;

import lombok.Builder;

@Builder
public record CompanyDto(String full_name, String email, String password) {
}

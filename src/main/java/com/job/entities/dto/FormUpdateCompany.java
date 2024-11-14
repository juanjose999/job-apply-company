package com.job.entities.dto;

import lombok.Builder;

@Builder
public record FormUpdateCompany(String email, CompanyDto  companyDto) {
}

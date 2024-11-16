package com.job.entities.company.dto;

import lombok.Builder;

@Builder
public record FormUpdateCompany(String email, CompanyDto  companyDto) {
}

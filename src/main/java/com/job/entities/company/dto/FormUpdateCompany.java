package com.job.entities.company.dto;

import lombok.Builder;

@Builder
public record FormUpdateCompany(String emailFindCompany, String full_name, String newEmailCompany, String password) {
}

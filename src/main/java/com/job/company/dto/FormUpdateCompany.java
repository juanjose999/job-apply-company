package com.job.company.dto;

import lombok.Builder;

@Builder
public record FormUpdateCompany(String emailFindCompany, String full_name, String newEmailCompany, String password) {
}

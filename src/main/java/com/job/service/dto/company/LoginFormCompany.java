package com.job.service.dto.company;

import lombok.Builder;

@Builder
public record LoginFormCompany(String nameCompany,
                               String password,
                               String email) {
}

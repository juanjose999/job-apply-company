package com.job.service.dto;

import lombok.Builder;

@Builder
public record LoginFormUser(String userName,
                            String password,
                            String email) {
}

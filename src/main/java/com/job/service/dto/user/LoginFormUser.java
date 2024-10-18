package com.job.service.dto.user;

import lombok.Builder;

@Builder
public record LoginFormUser(String userName,
                            String password,
                            String email) {
}

package com.job.service.dto;

import lombok.Builder;

@Builder
public record UserResponseDto(String id,
                              String userName,
                              String email) {
}

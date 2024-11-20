package com.job.entities.user.dto;

import lombok.Builder;

@Builder
public record MyUserDto(String first_name, String last_name, String email, String password) {
}

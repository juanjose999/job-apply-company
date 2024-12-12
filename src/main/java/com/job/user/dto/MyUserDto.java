package com.job.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MyUserDto(
        @NotBlank(message = "First name is required")
        String first_name,
        @NotBlank(message = "Last name is required")
        String last_name,
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password) {}

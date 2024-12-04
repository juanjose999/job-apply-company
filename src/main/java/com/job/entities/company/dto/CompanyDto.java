package com.job.entities.company.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CompanyDto( @NotEmpty(message = "name company cannot be empty")
                          String full_name,
                          @NotEmpty(message = "Email cannot be empty")
                          @Email(message = "Invalid email format")
                          String email,
                          String password) {
}

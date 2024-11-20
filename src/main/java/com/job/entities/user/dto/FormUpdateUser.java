package com.job.entities.user.dto;

import lombok.Builder;

@Builder
public record FormUpdateUser(String email, MyUserDto userDto) {
}

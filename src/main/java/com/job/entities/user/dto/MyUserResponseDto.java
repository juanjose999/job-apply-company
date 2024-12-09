package com.job.entities.user.dto;

import lombok.Builder;

@Builder
public record MyUserResponseDto(String name, String email, long counterAllApplications, long counterApplicationsRejected, long counterApplicationsToInterview) {
}

package com.job.entities.user.dto;

import lombok.Builder;

@Builder
public record MyUserResponseDto(String name, String email, String linkImgProfile, long counterAllApplications, long counterApplicationsRejected, long counterApplicationsToInterview) {
}

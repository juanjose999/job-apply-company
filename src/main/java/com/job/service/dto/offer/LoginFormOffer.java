package com.job.service.dto.offer;

import lombok.Builder;

@Builder
public record LoginFormOffer( String title,
                              String description,
                              String skills,
                              String date_created,
                              Boolean is_active) {
}

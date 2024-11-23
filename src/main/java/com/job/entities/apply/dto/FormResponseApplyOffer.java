package com.job.entities.apply.dto;

import lombok.Builder;

@Builder
public record FormResponseApplyOffer(String name_offer, String date_apply, String state) {
}

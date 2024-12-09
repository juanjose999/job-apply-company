package com.job.entities.offer_apply_user.dto;

import lombok.Builder;

@Builder
public record FormInfoUser(long idApply,String nameUser, String email, String applicationStatus, String dateApplication) {
}

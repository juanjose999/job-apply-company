package com.jobify.offer_user.dto;

import lombok.Builder;

@Builder
public record FormInfoUser(long idApply,String nameUser, String email, String applicationStatus, String dateApplication) {
}

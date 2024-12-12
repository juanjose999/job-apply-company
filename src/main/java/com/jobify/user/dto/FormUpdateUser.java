package com.jobify.user.dto;

import lombok.Builder;

@Builder
public record FormUpdateUser(String emailFindUser,String first_name, String last_name, String email, String password) {
}

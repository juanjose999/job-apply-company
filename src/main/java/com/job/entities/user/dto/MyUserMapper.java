package com.job.entities.user.dto;

import com.job.entities.offer_apply_user.StatusOffer;
import com.job.entities.user.MyUser;

import static com.job.entities.offer_apply_user.StatusOffer.INTERVIEWED;
import static com.job.entities.offer_apply_user.StatusOffer.REJECTED;

public class MyUserMapper {

    public static MyUser UserDtoToUser(MyUserDto userDto) {
        return MyUser.builder()
                .first_name(userDto.first_name())
                .last_name(userDto.last_name())
                .email(userDto.email())
                .password(userDto.password())
                .build();
    }

    public static MyUserResponseDto UserToUserDto(MyUser user) {
        return MyUserResponseDto.builder()
                .name(user.getFirst_name() + " " + user.getLast_name())
                .name(user.getFirst_name() + " " + user.getLast_name())
                .email(user.getEmail())
                .linkImgProfile(user.getUrlImgProfile())
                .counterAllApplications(user.getUserOffer() == null ? 0 : user.getUserOffer().size())
                .counterApplicationsToInterview(user.getUserOffer() == null ? 0 :
                        user.getUserOffer().stream()
                                .filter(s -> s.getStatus() == INTERVIEWED)
                                .count())
                .counterApplicationsRejected(user.getUserOffer() == null ? 0 :
                        user.getUserOffer().stream()
                                .filter(s -> s.getStatus() == REJECTED)
                                .count())
                .build();
    }

}

package com.jobify.user.dto;

import com.jobify.user.entity.MyUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.jobify.offer_user.entity.StatusOffer.INTERVIEWED;
import static com.jobify.offer_user.entity.StatusOffer.REJECTED;

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

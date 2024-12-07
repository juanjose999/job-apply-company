package com.job.entities.offer_apply_user.dto;

import com.job.entities.offer.Offer;
import com.job.entities.offer_apply_user.OfferApplyUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OfferApplyUserMapper {

    public static FormResponseApplyOffer offerApplyToResponse(OfferApplyUser offerApplyUser) {
        return FormResponseApplyOffer.builder()
                .name_offer(offerApplyUser.getOffer().getTitle())
                .date_apply(offerApplyUser.getDate_apply())
                .state(String.valueOf(offerApplyUser.getStatus()))
                .build();
    }

    public static OfferSingleWithAllApplications offerToOfferSingleWithAllApplications(Offer o, List<OfferApplyUser> offerApplyUserList) {
        return OfferSingleWithAllApplications.builder()
                .idOffer(o.getId())
                .title(o.getTitle())
                .description(o.getDescription())
                .requirements(o.getRequirements())
                .dateCreated(o.getDate_created())
                .ApplyUserList(offerApplyUserList)
                .build();
    }

}

package com.job.entities.apply.dto;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.offer.Offer;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.repository.apply.IApplyOffer;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class ApplyOfferMapper {


    public static FormResponseApplyOffer offerApplyToResponse(OfferApplyUser offerApplyUser) {
        return FormResponseApplyOffer.builder()
                .name_offer(offerApplyUser.getOffer().getTitle())
                .date_apply(offerApplyUser.getDate_apply())
                .state(String.valueOf(offerApplyUser.getStatus()))
                .build();
    }

}

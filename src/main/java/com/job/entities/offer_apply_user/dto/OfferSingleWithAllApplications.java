package com.job.entities.offer_apply_user.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.job.entities.offer_apply_user.OfferApplyUser;
import lombok.Builder;

import java.util.List;
@Builder
public record OfferSingleWithAllApplications(Long idOffer,
                                             String title,
                                             String description,
                                             String requirements,
                                             String dateCreated,
                                             @JsonManagedReference
                                     List<OfferApplyUser> ApplyUserList) {
}

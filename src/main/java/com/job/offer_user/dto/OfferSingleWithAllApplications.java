package com.job.offer_user.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;

import java.util.List;
@Builder
public record OfferSingleWithAllApplications(Long idOffer,
                                             String title,
                                             String description,
                                             String requirements,
                                             String dateCreated,
                                             int counterOfApplicants,
                                             @JsonManagedReference
                                             List<FormInfoUser> appliedUsers) {
}

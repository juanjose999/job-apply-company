package com.job.offer_user.repository;

import com.job.offer_user.entity.OfferApplyUser;
import com.job.offer.entity.Offer;
import com.job.user.entity.MyUser;
import com.job.shared.exception.exceptions.OfferNotFoundException;

import java.util.Optional;

public interface IApplyOffer {
     OfferApplyUser userApplyOffer(MyUser user, Offer offer);
     Optional<OfferApplyUser> findOfferApplyByIdOffer(Long id) throws OfferNotFoundException;
     OfferApplyUser saveApply(OfferApplyUser apply);
}

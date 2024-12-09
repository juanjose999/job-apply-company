package com.job.repository.apply;

import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import com.job.exception.exceptions.OfferNotFoundException;

import java.util.Optional;

public interface IApplyOffer {
     OfferApplyUser userApplyOffer(MyUser user, Offer offer);
     Optional<OfferApplyUser> findOfferApplyByIdOffer(Long id) throws OfferNotFoundException;
     OfferApplyUser saveApply(OfferApplyUser apply);
}

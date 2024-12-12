package com.jobify.offer_user.repository;

import com.jobify.offer_user.entity.OfferApplyUser;
import com.jobify.offer.entity.Offer;
import com.jobify.user.entity.MyUser;
import com.jobify.shared.exception.exceptions.OfferNotFoundException;

import java.util.Optional;

public interface IApplyOffer {
     OfferApplyUser userApplyOffer(MyUser user, Offer offer);
     Optional<OfferApplyUser> findOfferApplyByIdOffer(Long id) throws OfferNotFoundException;
     OfferApplyUser saveApply(OfferApplyUser apply);
}

package com.job.repository.apply;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;

public interface IApplyOffer {
     OfferApplyUser userApplyOffer(MyUser user, Offer offer);
}

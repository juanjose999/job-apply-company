package com.job.repository;

import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;

public interface IApplyOffer {
    String userApplyOffer(MyUser user, Offer offer);
}

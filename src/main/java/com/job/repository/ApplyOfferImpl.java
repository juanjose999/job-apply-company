package com.job.repository;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplyOfferImpl implements IApplyOffer{

    private final IApplyOfferJpa applyOfferJpa;

    @Override
    public String userApplyOffer(MyUser user, Offer offer) {
        OfferApplyUser offerApplyUser = OfferApplyUser.builder()
                .user(user)
                .offer(offer)
                .build();
        applyOfferJpa.save(offerApplyUser);
        return "Apply successfully";
    }
}

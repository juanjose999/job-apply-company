package com.job.repository.apply;

import com.job.entities.offer_apply_user.OfferApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IApplyOfferJpa extends JpaRepository<OfferApplyUser, Long> {
    List<OfferApplyUser> findByOfferId(Long offerId);
    List<OfferApplyUser> findByUserId(Long userId);
}

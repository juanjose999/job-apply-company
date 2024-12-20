package com.jobify.offer_user.repository;

import com.jobify.offer_user.entity.OfferApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IApplyOfferJpa extends JpaRepository<OfferApplyUser, Long> {
    List<OfferApplyUser> findByOfferId(Long offerId);
    List<OfferApplyUser> findByUserId(Long userId);
}

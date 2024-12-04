package com.job.repository.apply;

import com.job.entities.apply.OfferApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IApplyOfferJpa extends JpaRepository<OfferApplyUser, Long> {
    @Query(value = "SELECT COUNT(*) FROM apply", nativeQuery = true)
    int countAllApplyOffer();
    List<OfferApplyUser> findByOfferId(Long offerId);
}

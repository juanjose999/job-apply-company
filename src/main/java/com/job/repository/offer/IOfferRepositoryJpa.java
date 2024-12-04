package com.job.repository.offer;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOfferRepositoryJpa extends JpaRepository<Offer, Long> {
    Optional<List<Offer>> findByTitle(String title);
}

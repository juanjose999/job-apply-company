package com.job.repository.offer;

import com.job.entities.offer.Offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;

public interface IOfferRepositoryJpa extends JpaRepository<Offer, Long> {
    Optional<Set<Offer>> findByTitle(String title);
    Optional<Offer> findByTitleIgnoreCase(String title);
    Page<Offer> findAll(Pageable pageable);
}

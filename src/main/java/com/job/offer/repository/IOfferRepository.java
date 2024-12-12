package com.job.offer.repository;

import com.job.company.entity.Company;
import com.job.offer.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface IOfferRepository {
    Page<Offer> findAllOffers(Pageable pageable);
    Boolean findExistByTitleIgnoreCase(String title);
    Set<Offer> findAllOffersInsideCompany(Company company);
    Optional<Offer> findOfferById(Long id);
    Optional<Set<Offer>> findOfferByTitle(String title);
    Offer saveOffer(Offer offer, Company company);
    Optional<Offer> updateStateActiveOffer (Offer offer, boolean state);
    Offer updateOffer(Long idOffer, Offer offer, Company company);
    boolean deleteOffer(Offer offer, Company company);
}

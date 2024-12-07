package com.job.repository.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IOfferRepository {
    Set<Offer> findAllOffers();
    Set<Offer> findAllOffersInsideCompany(Company company);
    Optional<Offer> findOfferById(Long id);
    Optional<Set<Offer>> findOfferByTitle(String title);
    Offer saveOffer(Offer offer, Company company);
    Offer updateOffer(Long idOffer, Offer offer, Company company);
    boolean deleteOffer(Offer offer, Company company);
}

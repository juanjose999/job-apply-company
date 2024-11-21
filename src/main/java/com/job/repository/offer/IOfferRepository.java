package com.job.repository.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.OfferResponseDto;

import java.util.List;
import java.util.Optional;

public interface IOfferRepository {
    List<Offer> findAllOffers();
    List<Offer> findAllOffersInsideCompany(Company company);
    Optional<Offer> findOfferById(Long id);
    Optional<List<Offer>> findOfferByTitle(String title);
    Offer saveOffer(Offer offer, Company company);
    Offer updateOffer(Long idOffer, Offer offer, Company company);
    boolean deleteOffer(String nameOffer, Company company);
}

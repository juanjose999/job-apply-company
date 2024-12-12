package com.jobify.offer.repository;

import com.jobify.company.entity.Company;
import com.jobify.offer.entity.Offer;
import com.jobify.company.repository.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements IOfferRepository {

    private final IOfferRepositoryJpa offerRepository;
    private final ICompanyRepository companyRepository;

    @Override
    public Page<Offer> findAllOffers(Pageable pageable) {
        return offerRepository.findAll(pageable);
    }

    @Override
    public Boolean findExistByTitleIgnoreCase(String title) {
        Optional<Offer> offer = offerRepository.findByTitleIgnoreCase(title);
        return offer.isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Set<Offer> findAllOffersInsideCompany(Company company) {
        return company.getOffer();
    }

    @Override
    public Optional<Offer> findOfferById(Long id) {
        return offerRepository.findById(id);
    }

    @Override
    public Optional<Set<Offer>> findOfferByTitle(String title) {
        return offerRepository.findByTitle(title);
    }

    @Override
    public Offer saveOffer(Offer offer,Company company) {
        Offer offerSaved = offer;
        offerSaved.setDate_created();
        offerSaved.setCompany(company);
        offerSaved = offerRepository.save(offer);

        company.setOffer(offerSaved);
        companyRepository.saveCompany(company);

        return offerSaved;
    }

    @Override
    public Optional<Offer> updateStateActiveOffer (Offer offer, boolean state) {
        offer.setActive(state);
        return Optional.of(offerRepository.save(offer));
    }

    @Override
    public Offer updateOffer(Long idOffer, Offer offer, Company company) {
        Optional<Offer> foundOffer = offerRepository.findById(idOffer);
        Offer offerToUpdate = foundOffer.get();
        offerToUpdate.setTitle(offer.getTitle());
        offerToUpdate.setDescription(offer.getDescription());
        offerToUpdate.setRequirements(offer.getRequirements());
        offerToUpdate.setActive(offer.isActive());
        offerToUpdate.setDate_created(offer.getDate_created());
        offerToUpdate.setCompany(company);
        Offer offerSaved = offerRepository.save(offerToUpdate);
        company.setOffer(offerSaved);
        companyRepository.saveCompany(company);
        return offerSaved;
    }

    @Override
    public boolean deleteOffer(Offer offer, Company company) {
        Set<Offer> companyOffer = company.getOffer();
        for(Offer offerCurrent : companyOffer) {
            if(Objects.equals(offerCurrent.getId(), offer.getId())) {
                company.getOffer().remove(offer);
                companyRepository.saveCompany(company);
                offerRepository.delete(offer);
                return true;
            }
        }
        return false;
    }
}

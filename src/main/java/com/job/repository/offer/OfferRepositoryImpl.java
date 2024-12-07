package com.job.repository.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.repository.company.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements IOfferRepository {

    private final IOfferRepositoryJpa offerRepository;
    private final ICompanyRepository companyRepository;

    @Override
    public Set<Offer> findAllOffers() {
        return (Set<Offer>) offerRepository.findAll();
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

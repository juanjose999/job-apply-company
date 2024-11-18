package com.job.repository.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.OfferResponseDto;
import com.job.repository.company.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements IOfferRepository {

    private final IOfferRepositoryJpa offerRepository;
    private final ICompanyRepository companyRepository;

    @Override
    public List<Offer> findAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> findAllOffersInsideCompany(Company company) {
        return company.getOffer();
    }

    @Override
    public Optional<List<Offer>> findOfferByTitle(String title) {
        return offerRepository.findByTitle(title);
    }

    @Override
    public Offer saveOffer(Offer offer,Company company) {
        Offer offerSaved = offerRepository.save(offer);
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
    public boolean deleteOffer(String nameCompany, Company company) {
        List<Offer> companyOffer = company.getOffer();
        for(Offer offer : companyOffer) {
            if(nameCompany.equals(offer.getTitle())) {
                company.getOffer().remove(offer);
                companyRepository.saveCompany(company);
                offerRepository.delete(offer);
                return true;
            }
        }
        return false;
    }
}
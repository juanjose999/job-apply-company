package com.job.repository.offer;

import com.job.entities.Company;
import com.job.entities.Offert;
import com.job.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    private final OfferRepositoryJpa offerRepositoryJpa;
    private final CompanyRepository companyRepository;

    @Override
    public List<Offert> allOffert() {
        return offerRepositoryJpa.findAll();
    }

    @Override
    public Offert getOffertById(Long id) {
        return offerRepositoryJpa.findById(id).orElseThrow(
                () -> new RuntimeException("Offert not found"));
    }

    @Override
    public Offert saveOffert(Offert offert) {
        return offerRepositoryJpa.save(offert);
    }

    @Override
    public Offert updateOffert(Long id,Offert offert) {
        Offert findOffer = getOffertById(id);
        findOffer.setTitle(offert.getTitle());
        findOffer.setDescription(offert.getDescription());
        findOffer.setSkills(offert.getSkills());
        findOffer.setIs_active(offert.getIs_active());
        return offerRepositoryJpa.save(findOffer);
    }

    @Override
    public Boolean deleteOffert(Long id) {
        Offert findOffer = getOffertById(id);
        if(findOffer == null) {
            return false;
        }else{
            offerRepositoryJpa.delete(findOffer);
            return true;
        }
    }
}

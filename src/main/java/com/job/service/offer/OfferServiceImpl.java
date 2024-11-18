package com.job.service.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.*;
import com.job.exception.CompanyNotFoundException;
import com.job.exception.OfferNotFoundException;
import com.job.repository.company.ICompanyRepository;
import com.job.repository.offer.IOfferRepository;
import com.job.service.company.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements IOfferService {

    private final IOfferRepository  offerRepository;
    private final ICompanyRepository companyRepository;

    @Override
    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAllOffers().stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .toList();
    }

    @Override
    public List<OfferResponseDto> findAllOffersInsideCompany(String emailCompany) throws CompanyNotFoundException {
        Company findCompany = companyRepository.findCompanyByEmail(emailCompany).orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + emailCompany));
        return offerRepository.findAllOffersInsideCompany(findCompany).stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .toList();
    }

    @Override
    public List<OfferResponseDto> findOfferByTitle(String title) throws OfferNotFoundException {
        List<Offer> offers = offerRepository.findOfferByTitle(title).orElseThrow(() -> new OfferNotFoundException("Offer not found with title " + title));
        return offers.stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .toList();
    }

    @Override
    public OfferResponseDto saveOffer(FormSaveOffer formSaveOffer) throws CompanyNotFoundException {
        Company company = companyRepository.findCompanyByEmail(formSaveOffer.emailCompany()).orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formSaveOffer.emailCompany()));
        return OfferMapper.offerToOfferResponseDto(
                offerRepository.saveOffer(OfferMapper.offerDtoToOffer(formSaveOffer.offerDto()), company)
        );
    }

    @Override
    public OfferResponseDto updateOffer(FormUpdateOffer formUpdateOffer) throws CompanyNotFoundException {
        Company findCompany = companyRepository.findCompanyByEmail(formUpdateOffer.emailCompany()).orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formUpdateOffer.emailCompany()));
        if (findCompany != null) {
            return OfferMapper.offerToOfferResponseDto(
                    offerRepository.updateOffer(
                            formUpdateOffer.idOffer(), OfferMapper.offerDtoToOffer(formUpdateOffer.offerDto()), findCompany)
            );
        }else throw new CompanyNotFoundException("Company not found with email " + formUpdateOffer.emailCompany());
    }

    @Override
    public boolean deleteOffer(FormDeleteOffer formDeleteOffer) throws CompanyNotFoundException {
        Optional<Company> findCompany = Optional.ofNullable(companyRepository.findCompanyByEmail(formDeleteOffer.emailCompany()).orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formDeleteOffer.emailCompany())));
        return offerRepository.deleteOffer(formDeleteOffer.nameOffer(), findCompany.get());
    }
}
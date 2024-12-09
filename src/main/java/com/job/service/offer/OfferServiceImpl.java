package com.job.service.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.*;
import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer_apply_user.StatusOffer;
import com.job.entities.offer_apply_user.dto.FormFindApply;
import com.job.entities.offer_apply_user.dto.FormInfoUser;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.exception.exceptions.OfferExistException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.repository.apply.IApplyOffer;
import com.job.repository.company.ICompanyRepository;
import com.job.repository.offer.IOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements IOfferService {

    private final IOfferRepository  offerRepository;
    private final ICompanyRepository companyRepository;
    private final IApplyOffer applyOffer;

    @Override
    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAllOffers().stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .toList();
    }

    @Override
    public OfferResponseDto findOfferById(Long id) throws OfferNotFoundException {
        return OfferMapper.offerToOfferResponseDto(offerRepository.findOfferById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id " + id)));
    }

    @Override
    public FormInfoUser findApplyByEmailCompanyAndIdOffer(FormFindApply formFindApply) throws CompanyNotFoundException, OfferNotFoundException {
        Company company = companyRepository.findCompanyByEmail(formFindApply.emailCompany())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formFindApply.emailCompany()));

        if(company.getOffer() == null || company.getOffer().isEmpty()){
            throw new OfferNotFoundException("The company has no offers");
        }

        OfferApplyUser applyUser = company.getOffer().stream()
                .flatMap(o -> o.getUserOffers().stream())
                .filter(a -> Objects.equals(a.getId(), formFindApply.idApply()))
                .findFirst()
                .orElseThrow(() -> new OfferNotFoundException("No OfferApplyUser found with id " + formFindApply.idApply()));

        applyUser.setStatus(StatusOffer.OPEN);
        applyOffer.saveApply(applyUser);

        return FormInfoUser.builder()
                .idApply(applyUser.getId())
                .nameUser(applyUser.getUser().getFirst_name() +  " " + applyUser.getUser().getLast_name())
                .email(applyUser.getUser().getEmail())
                .applicationStatus(String.valueOf(applyUser.getStatus()).replaceAll("_", " ").toLowerCase())
                .dateApplication(applyUser.getDate_apply())
                .build();
    }

    @Override
    public List<OfferResponseDto> findAllOffersInsideCompany(String emailCompany) throws CompanyNotFoundException {
        System.out.println("emailCompany = " + emailCompany);
        Company findCompany = companyRepository.findCompanyByEmail(emailCompany)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + emailCompany));
        return offerRepository.findAllOffersInsideCompany(findCompany).stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .toList();
    }


    @Override
    public List<OfferResponseDto> findOfferByTitle(String title) throws OfferNotFoundException {
        Set<Offer> offers = offerRepository.findOfferByTitle(title).orElseThrow(() -> new OfferNotFoundException("Offer not found with title " + title));
        return offers.stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .toList();
    }

    @Override
    public OfferResponseDto saveOffer(FormSaveOffer formSaveOffer) throws OfferExistException, CompanyNotFoundException {
        Company company = companyRepository.findCompanyByEmail(formSaveOffer.emailCompany())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formSaveOffer.emailCompany()));

        if(offerRepository.findExistByTitleIgnoreCase(formSaveOffer.title())){
            throw new OfferExistException("The offer with the name, " + formSaveOffer.title() +
                    " already exists, please enter a name that does not exist in your offer list.");
        }

        OfferDto offerDto = OfferDto.builder()
                .title(formSaveOffer.title())
                .description(formSaveOffer.description())
                .requirements(formSaveOffer.requirements())
                .active(formSaveOffer.active())
                .build();

        return OfferMapper.offerToOfferResponseDto(
                offerRepository.saveOffer(OfferMapper.offerDtoToOffer(offerDto),company));
    }

    @Override
    public OfferResponseDto updateStateActiveOffer(OfferStatusUpdateForm offerStatusUpdateForm) throws CompanyNotFoundException, OfferNotFoundException {
        Company company = companyRepository.findCompanyByEmail(offerStatusUpdateForm.emailCompany())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + offerStatusUpdateForm.emailCompany()));

        if (company.getOffer().stream().noneMatch(o -> o.getId().equals(offerStatusUpdateForm.idOffer()))) {
            throw new OfferNotFoundException("Company does not contain offer with id " + offerStatusUpdateForm.idOffer());
        }

        Offer findOffer = offerRepository.findOfferById(offerStatusUpdateForm.idOffer())
                .orElseThrow(() -> new OfferNotFoundException("Offer not found"));

        return OfferMapper.offerToOfferResponseDto(offerRepository.updateStateActiveOffer(findOffer, offerStatusUpdateForm.state()).get());
    }



    @Override
    public OfferResponseDto updateOffer(FormUpdateOffer formUpdateOffer) throws CompanyNotFoundException {
        Company findCompany = companyRepository.findCompanyByEmail(formUpdateOffer.emailCompany())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formUpdateOffer.emailCompany()));
        OfferDto offerToUpdate = OfferDto.builder()
                .title(formUpdateOffer.title())
                .description(formUpdateOffer.description())
                .requirements(formUpdateOffer.requirements())
                .date_create(formUpdateOffer.date_create())
                .active(formUpdateOffer.active())
                .build();
        if (findCompany != null) {
            return OfferMapper.offerToOfferResponseDto(
                    offerRepository.updateOffer(
                            formUpdateOffer.idOffer(), OfferMapper.offerDtoToOffer(offerToUpdate), findCompany)
            );
        }else throw new CompanyNotFoundException("Company not found with email " + formUpdateOffer.emailCompany());
    }

    @Override
    public boolean deleteOffer(FormDeleteOffer formDeleteOffer) throws CompanyNotFoundException, OfferNotFoundException {
        Optional<Company> findCompany = Optional.ofNullable(companyRepository.findCompanyByEmail(formDeleteOffer.emailCompany())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formDeleteOffer.emailCompany())));

        Offer offer = offerRepository.findOfferById(formDeleteOffer.idOffer())
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id " + formDeleteOffer.idOffer()));

        return offerRepository.deleteOffer(offer, findCompany.get());
    }
}

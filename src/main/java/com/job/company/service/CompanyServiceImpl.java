package com.job.company.service;

import com.job.company.dto.*;
import com.job.company.entity.Company;
import com.job.offer.entity.Offer;
import com.job.offer_user.entity.OfferApplyUser;
import com.job.offer_user.entity.StatusOffer;
import com.job.offer_user.dto.OfferApplyUserMapper;
import com.job.offer_user.dto.OfferSingleWithAllApplications;
import com.job.offer_user.dto.OffersWithApplicationsResponseDto;
import com.job.shared.exception.exceptions.CompanyNotFoundException;
import com.job.shared.exception.exceptions.OfferNotFoundException;
import com.job.offer_user.repository.IApplyOfferJpa;
import com.job.company.repository.ICompanyRepository;
import com.job.offer.repository.IOfferRepository;
import com.job.shared.cloudinary.service.ICloudinaryService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService{

    private final ICompanyRepository companyRepository;
    private final IOfferRepository offerRepository;
    private final IApplyOfferJpa applyOfferJpa;
    private final ICloudinaryService cloudinaryService;

    private Company findCompany(String emailCompany) throws CompanyNotFoundException {
        return companyRepository.findCompanyByEmail(emailCompany)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + emailCompany));
    }

    private Offer findOfferById(Long id) throws OfferNotFoundException {
        return offerRepository.findOfferById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id " + id));
    }

    private OfferApplyUser findApplyByOfferId(Offer offer, Long idApply){
        return offer.getUserOffers().stream()
                .filter(o -> o.getId() == idApply)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CompanyResponseDto> findAllCompany() {
        List<Company> companies = companyRepository.findAllCompany();
        if(companies == null || companies.isEmpty()) {
            return Collections.emptyList();
        }
        List<CompanyResponseDto> companiesDto = new ArrayList<>();
        companies.forEach(company -> {
            Set<Offer> offer = company.getOffer();
            if(offer != null && !offer.isEmpty()) {
                List<OfferSingleWithAllApplications> offerSingle = offer.stream()
                        .map(o -> OfferApplyUserMapper.offerToOfferSingleWithAllApplications(o, applyOfferJpa.findByOfferId(o.getId()))).toList();
                companiesDto.add(CompanyMapper.CompanyToCompanyResponseDto(company, offerSingle));
            }else{
                companiesDto.add(CompanyMapper.CompanyToCompanyResponseDto(company, Collections.emptyList()));
            }
        });
        return companiesDto;
    }

    @Override
    public CompanyResponseDto saveCompany(CompanyDto companyDto) {
        return CompanyMapper.CompanyToCompanyResponseDto(companyRepository.saveCompany(CompanyMapper.CompanyDtoToCompany(companyDto)));
    }

    @Override
    public Either<String, String> uploadImgProfileCompany(MultipartFile file, String emailCompany) throws CompanyNotFoundException {
        Optional<Company> company = companyRepository.findCompanyByEmail(emailCompany);
        if(company.isEmpty()){
            return Either.left("Company not found");
        }
        try{
            String fileUpload = cloudinaryService.uploadImg(file.getBytes());
            company.get().setLinkImgProfile(fileUpload);
            companyRepository.saveCompany(company.get());
            return Either.right("url image profile = " + fileUpload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException {
        return CompanyMapper.CompanyToCompanyResponseDto(companyRepository.findCompanyByEmail(email)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + email)));
    }



    @Override
    public OffersWithApplicationsResponseDto findOffersWithApplicationsByEmailCompany(String emailCompany) throws CompanyNotFoundException {
        Company findCompany = findCompany(emailCompany);

        Set<Offer> offersList = findCompany.getOffer();

        List<OfferSingleWithAllApplications> offerSingleWithAllApplicationsList = offersList.stream()
                .map( o -> OfferApplyUserMapper.offerToOfferSingleWithAllApplications(o, applyOfferJpa.findByOfferId(o.getId())))
                .toList();

        return new OffersWithApplicationsResponseDto(findCompany.getFull_name(), offerSingleWithAllApplicationsList);
    }

    @Override
    public CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException {
        Company company = findCompany(formUpdateCompany.emailFindCompany());
        return CompanyMapper.CompanyToCompanyResponseDto(
                companyRepository.updateCompanyByEmail(formUpdateCompany.emailFindCompany(),
                        CompanyMapper.CompanyDtoToCompany(new CompanyDto(formUpdateCompany.full_name(), formUpdateCompany.newEmailCompany(), formUpdateCompany.password()))));
    }

    @Override
    public Either<String, String> updateStateOfferToRejectByEmailCompany(UpdateStateOfferInsideCompany updateStateOfferInsideCompany) throws CompanyNotFoundException, OfferNotFoundException {
        Company company = findCompany(updateStateOfferInsideCompany.emailCompany());
        Offer offer = findOfferById(updateStateOfferInsideCompany.idOffer());
        OfferApplyUser applyUser = findApplyByOfferId(offer, updateStateOfferInsideCompany.idOffer());
        if(applyUser == null) return Either.left("Offer apply  not found");
        applyUser.setStatus(StatusOffer.REJECTED);
        applyOfferJpa.save(applyUser);
        return Either.right("Offer apply rejected is successfully");
    }

    @Override
    public Either<String, String> updateStateOfferToInterviewedByEmailCompany(UpdateStateOfferInsideCompany updateStateOfferInsideCompany) throws CompanyNotFoundException, OfferNotFoundException {
        Company company = findCompany(updateStateOfferInsideCompany.emailCompany());
        Offer offer = findOfferById(updateStateOfferInsideCompany.idOffer());
        OfferApplyUser applyUser = findApplyByOfferId(offer, updateStateOfferInsideCompany.idOffer());
        if(applyUser == null) return Either.left("Offer apply  not found");
        applyUser.setStatus(StatusOffer.INTERVIEWED);
        applyOfferJpa.save(applyUser);
        return Either.right("Offer apply rejected is successfully");
    }


    @Override
    public boolean deleteCompanyByEmail(String email) {
        return companyRepository.deleteCompanyByEmail(email);
    }

}

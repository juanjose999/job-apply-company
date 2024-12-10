package com.job.service.company;

import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyMapper;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.entities.offer.Offer;
import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer_apply_user.dto.OfferApplyUserMapper;
import com.job.entities.offer_apply_user.dto.OfferSingleWithAllApplications;
import com.job.entities.offer_apply_user.dto.OffersWithApplicationsResponseDto;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.repository.apply.ApplyOfferImpl;
import com.job.repository.apply.IApplyOfferJpa;
import com.job.repository.company.ICompanyRepository;
import com.job.repository.offer.IOfferRepository;
import com.job.service.cloudinary.ICloudinaryService;
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

        Company findCompany = companyRepository.findCompanyByEmail(emailCompany)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + emailCompany));

        Set<Offer> offersList = findCompany.getOffer();

        List<OfferSingleWithAllApplications> offerSingleWithAllApplicationsList = offersList.stream()
                .map( o -> OfferApplyUserMapper.offerToOfferSingleWithAllApplications(o, applyOfferJpa.findByOfferId(o.getId())))
                .toList();

        return new OffersWithApplicationsResponseDto(findCompany.getFull_name(), offerSingleWithAllApplicationsList);
    }

    @Override
    public CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException {
        Company company = companyRepository.findCompanyByEmail(formUpdateCompany.emailFindCompany())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formUpdateCompany.emailFindCompany()));

        return CompanyMapper.CompanyToCompanyResponseDto(
                companyRepository.updateCompanyByEmail(formUpdateCompany.emailFindCompany(),
                        CompanyMapper.CompanyDtoToCompany(new CompanyDto(formUpdateCompany.full_name(), formUpdateCompany.newEmailCompany(), formUpdateCompany.password()))));
    }

    @Override
    public boolean deleteCompanyByEmail(String email) {
        return companyRepository.deleteCompanyByEmail(email);
    }

}

package com.job.service.offer;

import com.job.entities.Company;
import com.job.entities.Offert;
import com.job.repository.company.CompanyRepository;
import com.job.repository.offer.OfferRepository;
import com.job.service.dto.offer.FormSaveOfferInsideCompany;
import com.job.service.dto.offer.LoginFormOffer;
import com.job.service.dto.offer.OfferMapper;
import com.job.service.dto.offer.OfferResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements IOfferService{

    private final OfferRepository offerRepository;
    private final CompanyRepository companyRepository;

    @Override
    public List<OfferResponseDto> allOffert() {
        return offerRepository.allOffert().stream()
                .map(OfferMapper::EntityToOfferResponseDto)
                .toList();
    }

    @Override
    public OfferResponseDto getOffertById(Long id) {
        return OfferMapper.EntityToOfferResponseDto(offerRepository.getOffertById(id));
    }

    @Override
    public OfferResponseDto saveOffert(LoginFormOffer offert) {
        return OfferMapper.EntityToOfferResponseDto(
                offerRepository.saveOffert(
                        OfferMapper.OfferFormLoginToEntity(offert)));
    }

    @Override
    public OfferResponseDto saveOffertInsideCompany(FormSaveOfferInsideCompany formSaveOfferInsideCompany) {
        //bucar company, seteralr offer y guardar
        Company company = companyRepository.findByEmail(formSaveOfferInsideCompany.emailCompany());
        if(company==null){
            throw new RuntimeException("Company is empty");
        }
        Offert offert = OfferMapper.OfferFormLoginToEntity(formSaveOfferInsideCompany.formOffer());
        if(offert != null) {
            offert.setDate_created(String.valueOf(LocalDate.now()));
            offert.setCompanyList(company);
            offerRepository.saveOffert(offert);
            company.setOfferList(offert);
            companyRepository.saveCompany(company);
            return OfferMapper.EntityToOfferResponseDto(offert);
        }else{
            throw new RuntimeException("Offer is empty");
        }

    }

    @Override
    public OfferResponseDto updateOffert(Long id, LoginFormOffer offert) {
        return OfferMapper.EntityToOfferResponseDto(offerRepository.updateOffert(id, OfferMapper.OfferFormLoginToEntity(offert)));
    }

    @Override
    public Boolean deleteOffert(Long id) {
        return offerRepository.deleteOffert(id);
    }
}

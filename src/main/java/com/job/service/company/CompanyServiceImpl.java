package com.job.service.company;

import com.job.repository.company.CompanyRepository;
import com.job.service.dto.company.CompanyMapper;
import com.job.service.dto.company.CompanyResponseDto;
import com.job.service.dto.company.LoginFormCompany;
import com.job.service.dto.offer.FormSaveOfferInsideCompany;
import com.job.service.dto.offer.OfferResponseDto;
import com.job.service.offer.IOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final IOfferService offerService;


    @Override
    public List<CompanyResponseDto> findAll() {
        return companyRepository.findAll().stream()
                .map(CompanyMapper::EntityToRequest)
                .toList();
    }

    @Override
    public CompanyResponseDto findByEmail(String email) {
        return CompanyMapper.EntityToRequest(companyRepository.findByEmail(email));
    }

    @Override
    public CompanyResponseDto saveCompany(LoginFormCompany loginFormCompany) {
        return CompanyMapper.EntityToRequest(companyRepository.saveCompany(CompanyMapper.RequestToEntity(loginFormCompany)));
    }

    @Override
    public OfferResponseDto saveOfferInsideCompany(FormSaveOfferInsideCompany formSaveOfferInsideCompany) {
        return offerService.saveOffertInsideCompany(formSaveOfferInsideCompany);
    }

    @Override
    public CompanyResponseDto updateCompany(String email, LoginFormCompany loginFormCompany) {
        return CompanyMapper.EntityToRequest(companyRepository.updateCompany(email, CompanyMapper.RequestToEntity(loginFormCompany)));
    }

    @Override
    public Boolean deleteCompany(String email) {
        return companyRepository.deleteCompany(email);
    }
}

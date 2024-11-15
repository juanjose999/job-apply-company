package com.job.service;

import com.job.entities.Company;
import com.job.entities.dto.CompanyDto;
import com.job.entities.dto.CompanyMapper;
import com.job.entities.dto.CompanyResponseDto;
import com.job.entities.dto.FormUpdateCompany;
import com.job.exception.CompanyNotFoundException;
import com.job.repository.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService{

    private final ICompanyRepository companyRepository;

    @Override
    public List<CompanyResponseDto> findAllCompany() {
        return companyRepository.findAllCompany().stream()
                .map(CompanyMapper::CompanyToCompanyResponseDto)
                .toList();
    }

    @Override
    public CompanyResponseDto saveCompany(CompanyDto companyDto) {
        return CompanyMapper.CompanyToCompanyResponseDto(
                companyRepository.saveCompany
                        (CompanyMapper.CompanyDtoToCompany(companyDto))
        );
    }

    @Override
    public CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException {
        return CompanyMapper.CompanyToCompanyResponseDto(
                companyRepository.findCompanyByEmail(email)
                        .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + email)));
    }

    @Override
    public CompanyResponseDto updateCompanyByEmail(FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException {
        Company company = companyRepository.findCompanyByEmail(formUpdateCompany.email())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + formUpdateCompany.email()));
        return CompanyMapper.CompanyToCompanyResponseDto(companyRepository.updateCompanyByEmail(formUpdateCompany.email(), CompanyMapper.CompanyDtoToCompany(formUpdateCompany.companyDto())));
    }

    @Override
    public boolean deleteCompanyByEmail(String email) {
        return companyRepository.deleteCompanyByEmail(email);
    }

}

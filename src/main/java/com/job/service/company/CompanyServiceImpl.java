package com.job.service.company;

import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyMapper;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.repository.company.ICompanyRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                companyRepository.saveCompany(CompanyMapper.CompanyDtoToCompany(companyDto)));
    }

    @Override
    public CompanyResponseDto findCompanyByEmail(String email) throws CompanyNotFoundException {
        return CompanyMapper.CompanyToCompanyResponseDtoList(
                companyRepository.findCompanyByEmail(email)
                        .orElseThrow(() -> new CompanyNotFoundException("Company not found with email " + email)));
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

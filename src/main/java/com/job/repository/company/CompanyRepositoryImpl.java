package com.job.repository.company;

import com.job.entities.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository{

    private final CompanyRepositoryJpa companyRepositoryJpa;

    @Override
    public List<Company> findAll() {
        return companyRepositoryJpa.findAll();
    }

    @Override
    public Company findByEmail(String email) {
        return companyRepositoryJpa.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No company found with email " + email));
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepositoryJpa.save(company);
    }

    @Override
    public Company updateCompany(String email, Company company) {
        Company existingCompany = findByEmail(email);
        existingCompany.setName_company(company.getName_company());
        existingCompany.setEmail(company.getEmail());
        existingCompany.setPassword(company.getPassword());
        return companyRepositoryJpa.save(existingCompany);
    }

    @Override
    public Boolean deleteCompany(String email) {
        Company company = findByEmail(email);
        if(company != null) {
            companyRepositoryJpa.delete(company);
            return true;
        }else return false;
    }
}

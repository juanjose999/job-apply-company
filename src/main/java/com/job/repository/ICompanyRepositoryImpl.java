package com.job.repository;

import com.job.entities.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ICompanyRepositoryImpl implements ICompanyRepository {

    private final ICompanyRepositoryJpa ICompanyRepositoryJpa;

    @Override
    public List<Company> findAllCompany() {
        return ICompanyRepositoryJpa.findAll();
    }

    @Override
    public Company saveCompany(Company company) {
        return ICompanyRepositoryJpa.save(company);
    }

    @Override
    public Company findCompanyByEmail(String email) {
        return ICompanyRepositoryJpa.findCompanyByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public Company updateCompanyByEmail(String email, Company company) {
        Optional<Company> companyOptional = ICompanyRepositoryJpa.findCompanyByEmail(email);
        if(companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            if(company.getFull_name() != null) companyToUpdate.setFull_name(company.getFull_name());
            if(company.getEmail() != null) companyToUpdate.setEmail(company.getEmail());
            if(company.getPassword() != null) companyToUpdate.setPassword(company.getPassword());
            return ICompanyRepositoryJpa.save(companyToUpdate);
        }
        throw new RuntimeException("Company not found");
    }

    @Override
    public boolean deleteCompanyByEmail(String email) {
        if(ICompanyRepositoryJpa.existsCompaniesByEmail(email)) {
            ICompanyRepositoryJpa.deleteCompaniesByEmail(email);
            return true;
        }
        return false;
    }
}

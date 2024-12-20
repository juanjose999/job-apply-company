package com.jobify.company.repository;

import com.jobify.company.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ICompanyRepositoryImpl implements ICompanyRepository {

    private final ICompanyRepositoryJpa companyRepositoryJpa;

    @Override
    public List<Company> findAllCompany() {
        return companyRepositoryJpa.findAll();
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepositoryJpa.save(company);
    }

    @Override
    public Optional<Company> findCompanyByEmail(String email) {
        return companyRepositoryJpa.findCompanyByEmail(email);
    }

    @Override
    public Company updateCompanyByEmail(String email, Company company) {
        Optional<Company> companyOptional = companyRepositoryJpa.findCompanyByEmail(email);
        Company companyToUpdate = companyOptional.get();
        if(company.getFull_name() != null) companyToUpdate.setFull_name(company.getFull_name());
        if(company.getEmail() != null) companyToUpdate.setEmail(company.getEmail());
        if(company.getPassword() != null) companyToUpdate.setPassword(company.getPassword());
        return companyRepositoryJpa.save(companyToUpdate);
    }



    @Override
    public boolean deleteCompanyByEmail(String email) {
        if(companyRepositoryJpa.existsCompaniesByEmail(email)) {
            companyRepositoryJpa.deleteCompaniesByEmail(email);
            return true;
        }
        return false;
    }
}

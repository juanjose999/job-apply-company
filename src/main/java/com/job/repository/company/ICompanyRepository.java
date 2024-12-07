package com.job.repository.company;

import com.job.entities.company.Company;
import com.job.exception.exceptions.CompanyNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ICompanyRepository {
    List<Company> findAllCompany();
    Company saveCompany(Company company);
    Optional<Company> findCompanyByEmail(String email);
    Company updateCompanyByEmail(String email, Company company);
    boolean deleteCompanyByEmail(String email);
}

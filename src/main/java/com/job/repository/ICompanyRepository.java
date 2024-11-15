package com.job.repository;

import com.job.entities.Company;
import com.job.exception.CompanyNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ICompanyRepository {
    List<Company> findAllCompany();
    Company saveCompany(Company company);
    Optional<Company> findCompanyByEmail(String email) throws CompanyNotFoundException;
    Company updateCompanyByEmail(String email, Company company);
    boolean deleteCompanyByEmail(String email);
}

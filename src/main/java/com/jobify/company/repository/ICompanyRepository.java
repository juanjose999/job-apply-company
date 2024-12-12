package com.jobify.company.repository;

import com.jobify.company.entity.Company;

import java.util.List;
import java.util.Optional;

public interface ICompanyRepository {
    List<Company> findAllCompany();
    Company saveCompany(Company company);
    Optional<Company> findCompanyByEmail(String email);
    Company updateCompanyByEmail(String email, Company company);
    boolean deleteCompanyByEmail(String email);
}

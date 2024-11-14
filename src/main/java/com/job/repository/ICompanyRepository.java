package com.job.repository;

import com.job.entities.Company;

import java.util.List;

public interface ICompanyRepository {
    List<Company> findAllCompany();
    Company saveCompany(Company company);
    Company findCompanyByEmail(String email);
    Company updateCompanyByEmail(String email, Company company);
    boolean deleteCompanyByEmail(String email);
}

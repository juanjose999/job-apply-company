package com.job.repository.company;

import com.job.entities.Company;

import java.util.List;

public interface CompanyRepository {
    List<Company> findAll();
    Company findByEmail(String email);
    Company saveCompany(Company company);
    Company updateCompany(String email,Company company);
    Boolean deleteCompany(String email);
}

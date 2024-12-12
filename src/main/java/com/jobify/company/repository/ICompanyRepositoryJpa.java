package com.jobify.company.repository;

import com.jobify.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICompanyRepositoryJpa extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByEmail(String email);
    boolean existsCompaniesByEmail(String email);
    void deleteCompaniesByEmail(String email);
}

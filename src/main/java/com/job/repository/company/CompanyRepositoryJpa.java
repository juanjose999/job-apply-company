package com.job.repository.company;

import com.job.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepositoryJpa extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
}

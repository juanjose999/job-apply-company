package com.job.repository.offer;

import com.job.entities.Offert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepositoryJpa extends JpaRepository<Offert, Long> {
}

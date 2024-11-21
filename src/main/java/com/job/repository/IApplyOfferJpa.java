package com.job.repository;

import com.job.entities.apply.OfferApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IApplyOfferJpa extends JpaRepository<OfferApplyUser, Long> {
}

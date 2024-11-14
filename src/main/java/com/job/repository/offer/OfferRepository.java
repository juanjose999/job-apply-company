package com.job.repository.offer;

import com.job.entities.Company;
import com.job.entities.Offert;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
    List<Offert> allOffert();
    Offert getOffertById(Long id);
    Offert saveOffert(Offert offert);
    Offert updateOffert(Long id, Offert offert);
    Boolean deleteOffert(Long id);
}

package com.job.service.offer;

import com.job.entities.offer.dto.*;
import com.job.exception.CompanyNotFoundException;
import com.job.exception.OfferNotFoundException;
import lombok.Data;

import java.util.List;
import java.util.Optional;

public interface IOfferService {
    List<OfferResponseDto> findAllOffers();
    OfferResponseDto findOfferById(Long id) throws OfferNotFoundException;
    List<OfferResponseDto> findAllOffersInsideCompany(String emailCompany) throws CompanyNotFoundException;
    List<OfferResponseDto> findOfferByTitle(String title) throws OfferNotFoundException;
    OfferResponseDto saveOffer(FormSaveOffer formSaveOffer) throws CompanyNotFoundException;
    OfferResponseDto updateOffer(FormUpdateOffer formUpdateOffer) throws CompanyNotFoundException, OfferNotFoundException;
    boolean deleteOffer(FormDeleteOffer formDeleteOffer) throws CompanyNotFoundException, OfferNotFoundException;
}

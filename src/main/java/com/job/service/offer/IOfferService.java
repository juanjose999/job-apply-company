package com.job.service.offer;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.*;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.exception.exceptions.OfferExistException;
import com.job.exception.exceptions.OfferNotFoundException;

import java.util.List;

public interface IOfferService {
    List<OfferResponseDto> findAllOffers();
    OfferResponseDto findOfferById(Long id) throws OfferNotFoundException;
    List<OfferResponseDto> findAllOffersInsideCompany(String emailCompany) throws CompanyNotFoundException;
    List<OfferResponseDto> findOfferByTitle(String title) throws OfferNotFoundException;
    OfferResponseDto saveOffer(FormSaveOffer formSaveOffer) throws CompanyNotFoundException, OfferNotFoundException, OfferExistException;
    OfferResponseDto updateStateActiveOffer(OfferStatusUpdateForm offerStatusUpdateForm) throws CompanyNotFoundException, OfferNotFoundException;
    OfferResponseDto updateOffer(FormUpdateOffer formUpdateOffer) throws CompanyNotFoundException, OfferNotFoundException;
    boolean deleteOffer(FormDeleteOffer formDeleteOffer) throws CompanyNotFoundException, OfferNotFoundException;
}

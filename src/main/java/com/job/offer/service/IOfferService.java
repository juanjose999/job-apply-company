package com.job.offer.service;

import com.job.offer.dto.*;
import com.job.offer_user.dto.FormFindApply;
import com.job.offer_user.dto.FormInfoUser;
import com.job.shared.exception.exceptions.CompanyNotFoundException;
import com.job.shared.exception.exceptions.OfferExistException;
import com.job.shared.exception.exceptions.OfferNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOfferService {
    Page<OfferResponseDto> findAllOffers(int page, int size);
    OfferResponseDto findOfferById(Long id) throws OfferNotFoundException;
    FormInfoUser findApplyByEmailCompanyAndIdOffer(FormFindApply formFindApply) throws CompanyNotFoundException, OfferNotFoundException;
    List<OfferResponseDto> findAllOffersInsideCompany(String emailCompany) throws CompanyNotFoundException;
    List<OfferResponseDto> findOfferByTitle(String title) throws OfferNotFoundException;
    OfferResponseDto saveOffer(FormSaveOffer formSaveOffer) throws CompanyNotFoundException, OfferNotFoundException, OfferExistException;
    OfferResponseDto updateStateActiveOffer(OfferStatusUpdateForm offerStatusUpdateForm) throws CompanyNotFoundException, OfferNotFoundException;
    OfferResponseDto updateOffer(FormUpdateOffer formUpdateOffer) throws CompanyNotFoundException, OfferNotFoundException;
    boolean deleteOffer(FormDeleteOffer formDeleteOffer) throws CompanyNotFoundException, OfferNotFoundException;
}

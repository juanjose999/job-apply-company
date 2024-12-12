package com.jobify.offer.service;

import com.jobify.offer.dto.*;
import com.jobify.offer_user.dto.FormFindApply;
import com.jobify.offer_user.dto.FormInfoUser;
import com.jobify.shared.exception.exceptions.CompanyNotFoundException;
import com.jobify.shared.exception.exceptions.OfferExistException;
import com.jobify.shared.exception.exceptions.OfferNotFoundException;
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

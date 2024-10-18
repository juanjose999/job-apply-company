package com.job.service.offer;

import com.job.entities.Offert;
import com.job.service.dto.offer.FormSaveOfferInsideCompany;
import com.job.service.dto.offer.LoginFormOffer;
import com.job.service.dto.offer.OfferResponseDto;

import java.util.List;

public interface IOfferService {
    List<OfferResponseDto> allOffert();
    OfferResponseDto getOffertById(Long id);
    OfferResponseDto saveOffert(LoginFormOffer offert);
    OfferResponseDto saveOffertInsideCompany(FormSaveOfferInsideCompany formSaveOfferInsideCompany);
    OfferResponseDto updateOffert(Long id,LoginFormOffer offert);
    Boolean deleteOffert(Long id);
}

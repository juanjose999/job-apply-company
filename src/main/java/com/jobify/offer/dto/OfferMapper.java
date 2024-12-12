package com.jobify.offer.dto;

import com.jobify.offer.entity.Offer;
import com.jobify.offer_user.repository.IApplyOffer;

public class OfferMapper {

    private final IApplyOffer applyOfferService; // Instancia del servicio

    // Constructor
    public OfferMapper(IApplyOffer applyOfferService) {
        this.applyOfferService = applyOfferService;
    }

    public static Offer offerDtoToOffer(OfferDto offerDto) {
        return Offer.builder()
                .title(offerDto.title())
                .description(offerDto.description())
                .requirements(offerDto.requirements())
                .date_created(offerDto.date_create())
                .isActive(offerDto.active())
                .build();
    }

    public static OfferResponseDto offerToOfferResponseDto(Offer offer) {

        return OfferResponseDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .requirements(offer.getRequirements())
                .date_create(offer.getDate_created())
                .build();
    }

}

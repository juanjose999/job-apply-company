package com.job.entities.offer.dto;

import com.job.entities.offer.Offer;
import com.job.repository.apply.IApplyOffer;

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
                .active(offerDto.active())
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

package com.job.controller;

import com.job.entities.offer.dto.*;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.service.offer.IOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${app.version}/offers")
@RequiredArgsConstructor
public class OfferController {

    private final IOfferService offerService;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffers() {
        return ResponseEntity.ok(offerService.findAllOffers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable Long id) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.findOfferById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<OfferResponseDto>> getOfferByEmailCompany(@PathVariable String email) throws OfferNotFoundException, CompanyNotFoundException {
        return ResponseEntity.ok(offerService.findAllOffersInsideCompany(email));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<OfferResponseDto>> getOfferByTitle(@PathVariable String title) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.findOfferByTitle(title));
    }

    @PostMapping
    public ResponseEntity<OfferResponseDto> saveOffer(@RequestBody FormSaveOffer formSaveOffer) throws CompanyNotFoundException {
        return ResponseEntity.ok(offerService.saveOffer(formSaveOffer));
    }

    @PutMapping()
    public ResponseEntity<OfferResponseDto> updateOffer(@RequestBody FormUpdateOffer formUpdateOffer) throws OfferNotFoundException, CompanyNotFoundException {
        return ResponseEntity.ok(offerService.updateOffer(formUpdateOffer));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOffer(@RequestBody FormDeleteOffer formDeleteOffer) throws OfferNotFoundException, CompanyNotFoundException {
        return offerService.deleteOffer(formDeleteOffer) ?  ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

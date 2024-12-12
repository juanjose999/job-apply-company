package com.jobify.offer.controller;
import com.jobify.offer.dto.*;
import com.jobify.offer_user.dto.FormFindApply;
import com.jobify.shared.exception.exceptions.CompanyNotFoundException;
import com.jobify.shared.exception.exceptions.OfferExistException;
import com.jobify.shared.exception.exceptions.OfferNotFoundException;
import com.jobify.offer.service.IOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${app.version}/offers")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OfferController {

    private final IOfferService offerService;

    @GetMapping
    public ResponseEntity<Page<OfferResponseDto>> getAllOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        return ResponseEntity.ok(offerService.findAllOffers(page,size));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable Long id) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.findOfferById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<OfferResponseDto>> getOffersByEmailCompany(@PathVariable String email) throws OfferNotFoundException, CompanyNotFoundException {
        return ResponseEntity.ok(offerService.findAllOffersInsideCompany(email));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<OfferResponseDto>> getOfferByTitle(@PathVariable String title) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.findOfferByTitle(title));
    }

    @PostMapping
    public ResponseEntity<OfferResponseDto> saveOffer(@RequestBody FormSaveOffer formSaveOffer) throws CompanyNotFoundException, OfferExistException, OfferNotFoundException {
        return ResponseEntity.ok(offerService.saveOffer(formSaveOffer));
    }

    @GetMapping("/applications")
    public ResponseEntity<?> findApplyByCompanyEmailAndIdOffer(@RequestBody FormFindApply formFindApply) throws CompanyNotFoundException, OfferNotFoundException {
        return ResponseEntity.ok(offerService.findApplyByEmailCompanyAndIdOffer(formFindApply));
    }

    @PutMapping()
    public ResponseEntity<OfferResponseDto> updateOffer(@RequestBody FormUpdateOffer formUpdateOffer) throws OfferNotFoundException, CompanyNotFoundException {
        return ResponseEntity.ok(offerService.updateOffer(formUpdateOffer));
    }

    @PutMapping("/state")
    public ResponseEntity<OfferResponseDto> updateOfferState(@RequestBody OfferStatusUpdateForm formUpdateOffer) throws OfferNotFoundException, CompanyNotFoundException {
        return ResponseEntity.ok(offerService.updateStateActiveOffer(formUpdateOffer));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOffer(@RequestBody FormDeleteOffer formDeleteOffer) throws OfferNotFoundException, CompanyNotFoundException {
        return offerService.deleteOffer(formDeleteOffer) ?  ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

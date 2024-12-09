package com.job.controller;

import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.entities.offer.dto.OfferResponseDto;
import com.job.entities.offer_apply_user.dto.OffersWithApplicationsResponseDto;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.service.company.ICompanyService;
import com.job.service.offer.IOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${app.version}/companies")
public class CompanyController {

    private final ICompanyService companyService;
    private final IOfferService offerService;

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> findAllCompanies(){
        return ResponseEntity.ok(companyService.findAllCompany());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CompanyResponseDto> findCompanyByEmail(@PathVariable String email) throws CompanyNotFoundException {
        return ResponseEntity.ok(companyService.findCompanyByEmail(email));
    }

    @GetMapping("/offers-with-applications")
    public ResponseEntity<OffersWithApplicationsResponseDto> findOffersWithApplicationsByEmailCompany(@RequestBody Map<String, String> email) throws OfferNotFoundException, CompanyNotFoundException {
        System.out.println(email.get("email"));
        return ResponseEntity.ok(companyService.findOffersWithApplicationsByEmailCompany(email.get("email")));
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> saveCompany(@RequestBody CompanyDto companyDto){
        CompanyResponseDto companyResponseDto = companyService.saveCompany(companyDto);
        if(companyResponseDto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(companyResponseDto);
    }

    @PutMapping
    public ResponseEntity<CompanyResponseDto> updateCompany(@RequestBody FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException {
        return ResponseEntity.ok(companyService.updateCompanyByEmail(formUpdateCompany));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCompanyByEmail(@RequestBody String email) throws CompanyNotFoundException {
        return companyService.deleteCompanyByEmail(email) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

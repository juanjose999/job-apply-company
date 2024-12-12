package com.job.company.controller;

import com.job.company.dto.CompanyDto;
import com.job.company.dto.CompanyResponseDto;
import com.job.company.dto.FormUpdateCompany;
import com.job.company.dto.UpdateStateOfferInsideCompany;
import com.job.offer_user.dto.OffersWithApplicationsResponseDto;
import com.job.shared.exception.exceptions.CompanyNotFoundException;
import com.job.shared.exception.exceptions.OfferNotFoundException;
import com.job.shared.cloudinary.service.CloudinaryService;
import com.job.company.service.ICompanyService;
import com.job.offer.service.IOfferService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${app.version}/companies")
public class CompanyController {

    private final ICompanyService companyService;
    private final IOfferService offerService;
    private final CloudinaryService cloudinaryService;

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

    @PostMapping("/upload/img/profile")
    public ResponseEntity<?> uploadImgProfileCompany(@RequestParam("file")MultipartFile file, @RequestParam String emailCompany) throws IOException, CompanyNotFoundException {
        Either<String, String> saveImgProfile = companyService.uploadImgProfileCompany(file, emailCompany);
        if(saveImgProfile.isLeft()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saveImgProfile.get());
    }

    @PutMapping
    public ResponseEntity<CompanyResponseDto> updateCompany(@RequestBody FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException {
        return ResponseEntity.ok(companyService.updateCompanyByEmail(formUpdateCompany));
    }

    @PatchMapping("/state-to-reject")
    public ResponseEntity<?> updateStateOfferToRejectByEmailCompany(@RequestBody UpdateStateOfferInsideCompany updateStateOfferInsideCompany) throws CompanyNotFoundException, OfferNotFoundException {
        Either<String, String> resultUpdateState = companyService.updateStateOfferToRejectByEmailCompany(updateStateOfferInsideCompany);
        if(resultUpdateState.isLeft()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("correct update of the offer status");
    }

    @PatchMapping("/state-to-interviewed")
    public ResponseEntity<?> updateStateOfferToInterviewedByEmailCompany(@RequestBody UpdateStateOfferInsideCompany updateStateOfferInsideCompany) throws CompanyNotFoundException, OfferNotFoundException {
        Either<String, String> resultUpdateState = companyService.updateStateOfferToRejectByEmailCompany(updateStateOfferInsideCompany);
        if(resultUpdateState.isLeft()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("correct update of the offer status");
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCompanyByEmail(@RequestBody String email) throws CompanyNotFoundException {
        return companyService.deleteCompanyByEmail(email) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

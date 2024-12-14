package com.jobify.company.controller;

import com.jobify.company.dto.CompanyDto;
import com.jobify.company.dto.CompanyResponseDto;
import com.jobify.company.dto.FormUpdateCompany;
import com.jobify.company.dto.UpdateStateOfferInsideCompany;
import com.jobify.offer_user.dto.OffersWithApplicationsResponseDto;
import com.jobify.shared.exception.exceptions.CompanyNotFoundException;
import com.jobify.shared.exception.exceptions.OfferNotFoundException;
import com.jobify.shared.cloudinary.service.CloudinaryService;
import com.jobify.company.service.ICompanyService;
import com.jobify.offer.service.IOfferService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/${app.version}/companies")
public class CompanyController {

    private final ICompanyService companyService;
    private final IOfferService offerService;
    private final CloudinaryService cloudinaryService;
    private final AuthenticationManager authentication;

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

    @PostMapping("/register")
    public ResponseEntity<CompanyResponseDto> saveCompany(@RequestBody CompanyDto companyDto){
        CompanyResponseDto companyResponseDto = companyService.saveCompany(companyDto);
        if(companyResponseDto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(companyResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCompany(@RequestBody FormLogin formLogin){
        Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(formLogin.username(), formLogin.password()));
        if(auth.isAuthenticated()){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println("El usuario con el nombre + " + userDetails.getUsername()  +" ha iniciado seccion");
            return ResponseEntity.ok(userDetails);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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

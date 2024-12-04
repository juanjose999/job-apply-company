package com.job.controller;

import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.company.dto.FormUpdateCompany;
import com.job.exception.exceptions.CompanyNotFoundException;
import com.job.service.company.ICompanyService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/companies")
public class CompanyController {

    private final ICompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> findAllCompanies(){
        return ResponseEntity.ok(companyService.findAllCompany());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CompanyResponseDto> findCompanyByEmail(@PathVariable String email) throws CompanyNotFoundException {
        return ResponseEntity.ok(companyService.findCompanyByEmail(email));
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> saveCompany(@RequestBody CompanyDto companyDto){
        CompanyResponseDto companyResponseDto = companyService.saveCompany(companyDto);
        if(companyResponseDto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(companyResponseDto);
    }

    @PutMapping("/email")
    public ResponseEntity<CompanyResponseDto> updateCompany(@RequestBody FormUpdateCompany formUpdateCompany) throws CompanyNotFoundException {
        return ResponseEntity.ok(companyService.updateCompanyByEmail(formUpdateCompany));
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteCompanyByEmail(@PathVariable  String email) throws CompanyNotFoundException {
        return companyService.deleteCompanyByEmail(email) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

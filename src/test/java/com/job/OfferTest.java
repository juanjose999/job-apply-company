package com.job;

import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyMapper;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.*;
import com.job.exception.CompanyNotFoundException;
import com.job.exception.OfferNotFoundException;
import com.job.repository.company.ICompanyRepository;
import com.job.repository.offer.OfferRepositoryImpl;
import com.job.service.offer.OfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferTest {

    @Mock
    private OfferRepositoryImpl offerRepository;

    @Mock
    private ICompanyRepository companyRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    public void should_create_offerDto_insideCompany_return_offerResponseDto() throws CompanyNotFoundException {
        // formulario de registro de compañia
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        // objeto registrado
        Company company = Company.builder()
                .id(1L)
                .full_name("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();

        //simulo la creacion y la busqueda de la compañia
        when(companyRepository.findCompanyByEmail("hola@gmail.com")).thenReturn(Optional.of(company));

        //formulario de registro de oferta
        OfferDto offerDto = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();

        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(1L);
        offer.setCompany(company);
        company.setOffer(offer);

        FormSaveOffer formSaveOffer = FormSaveOffer.builder()
                .offerDto(offerDto)
                .emailCompany("hola@gmail.com")
                .build();

        when(companyRepository.findCompanyByEmail("hola@gmail.com")).thenReturn(Optional.of(company));
        when(offerRepository.saveOffer(any(Offer.class), any(Company.class))).thenReturn(offer);
        OfferResponseDto offerResponseDto = offerService.saveOffer(formSaveOffer);

        assertNotNull(offerResponseDto);
        assertEquals(company, offer.getCompany());
        assertEquals(offer.getCompany(), company);
        assertEquals(offer.getCompany().getEmail(), company.getEmail());
        assertEquals(company.getId(), offer.getCompany().getId());
        assertEquals("java dev", offer.getTitle());
    }

    @Test
    public void should_create_offerWithCompanyEmpty_return_exceptionOffer() throws CompanyNotFoundException {
        OfferDto offerDto = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();

        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(1L);
        offer.setCompany(null);

        FormSaveOffer formSaveOffer = FormSaveOffer.builder()
                .offerDto(offerDto)
                .emailCompany("noexist@gmail.com")
                .build();

        when(companyRepository.findCompanyByEmail("noexist@gmail.com")).thenReturn(Optional.empty());
        CompanyNotFoundException exception = assertThrows(
                CompanyNotFoundException.class,
                () -> offerService.saveOffer(formSaveOffer)
        );
        assertEquals("Company not found with email " + formSaveOffer.emailCompany(), exception.getMessage());
    }

    @Test
    public void should_findAllOffer_byCompany_return_company_ListOfferResponseDto() throws CompanyNotFoundException {
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);

        offer.setCompany(company);
        company.setOffer(offer);

        OfferDto offerDto2 = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer2 = OfferMapper.offerDtoToOffer(offerDto2);
        company.setOffer(offer2);
        offer.setCompany(company);

        OfferDto offerDto3 = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer3 = OfferMapper.offerDtoToOffer(offerDto3);
        company.setOffer(offer3);
        offer.setCompany(company);

        List<Offer> offerList = new ArrayList<>();
        offerList.add(offer);
        offerList.add(offer2);
        offerList.add(offer3);

        List<OfferResponseDto> offerResponseDtoList = offerList.stream()
                        .map(OfferMapper::offerToOfferResponseDto)
                        .toList();

        when(companyRepository.findCompanyByEmail("hola@gmail.com")).thenReturn(Optional.of(company));
        when(offerRepository.findAllOffersInsideCompany(any(Company.class))).thenReturn(offerList);
        List<OfferResponseDto> result = offerService.findAllOffersInsideCompany("hola@gmail.com");

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(OfferMapper.offerToOfferResponseDto(offer), result.get(0));
        assertEquals("java dev", result.get(2).title());
    }

    @Test
    public void should_offerByTitle_return_offerResponseDto() throws OfferNotFoundException {
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        company.setOffer(offer);
        offer.setCompany(company);

        OfferDto offerDto2 = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer2 = OfferMapper.offerDtoToOffer(offerDto);
        company.setOffer(offer2);
        offer.setCompany(company);

        List<Offer> offerList = List.of(offer,offer2);

        when(offerRepository.findOfferByTitle("java dev")).thenReturn(Optional.of(offerList));
        List<OfferResponseDto> offerResponseDto = offerService.findOfferByTitle("java dev");

        assertNotNull(offerResponseDto);
        assertEquals(2, offerResponseDto.size());
    }

    @Test
    public void should_updateOfferByEmailCompany_return_offerResponseDto() throws CompanyNotFoundException {
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);
        company.setId(1L);

        when(companyRepository.findCompanyByEmail(any(String.class))).thenReturn(Optional.of(company));

        OfferDto offerDto = OfferDto.builder()
                .title("java dev")
                .description("dev use java and spring boot")
                .requirements("java, spring, mysql")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        company.setOffer(offer);
        offer.setCompany(company);

        OfferDto offerDto2 = OfferDto.builder()
                .title("cobol dev")
                .description("cobol dev use binary lang")
                .requirements("cobol, oracle")
                .date_create(String.valueOf(LocalDateTime.now()))
                .active(true)
                .build();
        Offer offer2 = OfferMapper.offerDtoToOffer(offerDto2);

        OfferDto offerUpdate = OfferDto.builder()
                .title("carJs framework")
                .description("senior expert in js")
                .requirements("10 years work in js")
                .build();

        FormUpdateOffer formUpdateOffer = FormUpdateOffer.builder()
                .idOffer(1L)
                .offerDto(offerUpdate)
                .emailCompany("hola@gmail.com")
                .build();

        when(offerRepository.updateOffer(any(Long.class), any(Offer.class), any(Company.class))).thenReturn(OfferMapper.offerDtoToOffer(offerUpdate));
        OfferResponseDto offerResponseDto = offerService.updateOffer(formUpdateOffer);

        assertNotNull(offerResponseDto);
        assertEquals("carJs framework", offerResponseDto.title());
        assertEquals("senior expert in js", offerResponseDto.description());
    }

    @Test
    public void should_deleteOfferByIdWithCompany_return_true() throws CompanyNotFoundException {
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto1 = OfferDto.builder()
                .title("carJs framework")
                .description("senior expert in js")
                .requirements("10 years work in js")
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto1);
        company.setOffer(offer);
        offer.setCompany(company);

        when(companyRepository.findCompanyByEmail("hola@gmail.com")).thenReturn(Optional.of(company));
        when(offerRepository.deleteOffer(any(String.class), any(Company.class))).thenReturn(true);
        boolean resultOfferDelete = offerService.deleteOffer(new FormDeleteOffer("carJs framework", "hola@gmail.com"));

        assertTrue(resultOfferDelete);
    }
}

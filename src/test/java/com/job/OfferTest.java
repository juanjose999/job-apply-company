package com.job;

import com.job.entities.Company;
import com.job.entities.dto.CompanyDto;
import com.job.entities.dto.CompanyMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferTest {

    @Mock
    private OfferRepositoryImpl offerRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    public void should_create_offerDto_insideCompany_return_offerResponseDto(){
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);
        OfferDto offerDto = Offer.build().build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        company.setOffer(offer);
        offer.setCompany(company);

        when(offerRepository.saveOffer(Offer.class)).thenReturn(offer);
        OfferResponseDto offerResponseDto = offerService.saveOffer(offer);

        assertNotNull(offerResponseDto);
        asserEquals("hola company", offerDto.getCompany().getEmail());
        assetEquals(offer.getTitle(), offerDto.getTitle());
    }

    @Test
    public void should_create_offerWithCompanyEmpty_return_exceptionOffer(){
        OfferDto offerDto = Offer.build().build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        company.setOffer(offer);
        offer.setCompany(null);

        when(offerRepository.saveOffer(Offer.class)).thenReturn(Optional.isEmpty());
        Exception offerException = offerService.saveOffer(OfferMapper.offerToOfferDto(offer));

        assertTrow("Company is empty", offerException.getMessage());
    }

    @Test
    public void should_findAllOffer_byCompany_return_company_ListOffer(){

        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto1 = Offer.build().build();
        Offer offer1 = OfferMapper.offerDtoToOffer(offerDto1);
        company.setOffer(offer1);
        offer.setCompany(company);

        OfferDto offerDto2 = Offer.build().build();
        Offer offer2 = OfferMapper.offerDtoToOffer(offerDto1);
        company.setOffer(offer2);
        offer.setCompany(company);

        OfferDto offerDto3 = Offer.build().build();
        Offer offer3 = OfferMapper.offerDtoToOffer(offerDto1);
        company.setOffer(offer3);
        offer.setCompany(company);

        List<Offer> offerList = new ArrayList<>();
        offerList.add(offer1);
        offerList.add(offer2);
        offerList.add(offer3);

        List<OfferResponseDto> offerResponseDtoList = new ArrayList<>();
        offerResponseDtoList.add(OfferMapper.offerToOfferDto(offer1));
        offerResponseDtoList.add(OfferMapper.offerToOfferDto(offer2));
        offerResponseDtoList.add(OfferMapper.offerToOfferDto(offer3));

        when(offerRepository.findOfferInsideCompany(any(Company.class))).thenReturn(offerList);
        List<OfferResponseDto> result = offerService.findOfferInsideCompany(companyDto);

        assertNotNull(result);
        assertEquals(3, result.length());
        assertEquals("java dev", result.get(0).getName());
        assertEquals("python", result.get(1).getName());

    }

    @Test
    public void should_offerByTitle_return_offerResponseDto(){
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto1 = Offer.build().build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto1);
        company.setOffer(offer);
        offer.setCompany(company);


        when(offerRepository.findOfferByTitle("java dev")).thenReturn(offer);
        OfferResponseDto offerResponseDto = offerService.findOfferByTitle("java dev");

        assertNotNull(offerResponseDto);
        assertEquals("java dev", offerResponseDto.getTitle());
    }

    @Test
    public void should_updateOfferByEmailCompany_return_offerResponseDto(){
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto1 = Offer.build()
                .title("python dev")
                .description("mmm mmm mmm")
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto1);
        company.setOffer(offer);
        offer.setCompany(company);

        OfferDto offerUpdate = Offer.Build()
                .title("java dev")
                .description("123 xxx 000")
                .build();
        Offer offerDataToUpdate = OfferMapper.offerDtoOffer(offerUpdate);

        when(offerRepository.updateOffer(eq(String.class),(Offer.class))).thenReturn(offerDataToUpdate);
        OfferResponseDto offerResponseDto = offerService.updateOffer(email, offerUpdate);

        assertNotNull(offerResponseDto);
        asserEquals("java dev", offerResponseDto.title());
        asserEquals("123 xxx 000", offerResponseDto.description());
    }

    @Test
    public void should_deleteOfferByIdWithEmailCompany_return_true(){
        CompanyDto formSaveCompany = CompanyDto.builder()
                .fullName("hola company")
                .email("hola@gmail.com")
                .password("123xxx")
                .build();
        Company company = CompanyMapper.CompanyDtoToCompany(formSaveCompany);

        OfferDto offerDto1 = Offer.build()
                .title("python dev")
                .description("mmm mmm mmm")
                .build();

        when(offerRepository.deleteOfferByIdWithEmailCompany(eq(String.class),(String.class))).thenReturn(true);
        OfferResponseDto offerResponseDto = offerService.deleteOfferByIdWithEmailCompany(company.getEmail, String titleCompany);

        assertNull(offerResponseDto);
    }
}

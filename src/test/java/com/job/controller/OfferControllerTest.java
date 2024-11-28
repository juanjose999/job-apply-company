package com.job.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.company.Company;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.company.dto.CompanyResponseDto;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.*;
import com.job.service.company.CompanyServiceImpl;
import com.job.service.offer.OfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferServiceImpl offerService;

    @MockBean
    private CompanyServiceImpl companyService;

    @Test
    void should_save_offerDto_inside_company_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDtoNotSaved = objectMapper.readValue(readerOffer, OfferDto.class);
        FormSaveOffer formSaveOffer = FormSaveOffer.builder()
                .title(offerDtoNotSaved.title())
                .description(offerDtoNotSaved.description())
                .requirements(offerDtoNotSaved.requirements())
                .active(offerDtoNotSaved.active())
                .date_created(offerDtoNotSaved.date_create())
                .emailCompany("hola@gmail.com")
                .build();
        OfferResponseDto offerResponseDto = OfferResponseDto.builder()
                .title(offerDtoNotSaved.title())
                .description(offerDtoNotSaved.description())
                .requirements(offerDtoNotSaved.requirements())
                .date_create(offerDtoNotSaved.date_create())
                .build();

        String jsonSaved = objectMapper.writeValueAsString(formSaveOffer);


        Mockito.when(offerService.saveOffer(formSaveOffer)).thenReturn(offerResponseDto);

        mockMvc.perform(post("/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSaved))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(offerDtoNotSaved.title()))
                .andExpect(jsonPath("$.description").value(offerDtoNotSaved.description()))
                .andExpect(jsonPath("$.requirements").value(offerDtoNotSaved.requirements()));
        Mockito.verify(offerService).saveOffer(formSaveOffer);
    }

//    @Test
//    void should_findOfferByInsideCompany_return_200() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//
//        File readerOfferResponse = new File("src/test/java/resource/OfferListResponseDto.json");
//        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerOfferResponse, new TypeReference<List<OfferResponseDto>>() {
//        });
//        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
//        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
//        Company company = Company.builder()
//                .id(1l)
//                .full_name(companyDto.full_name())
//                .email(companyDto.email())
//                .password(companyDto.password())
//                .build();
//        CompanyResponseDto findCompany = CompanyResponseDto.builder()
//                .email(companyDto.email())
//                .fullName(companyDto.full_name())
//                .build();
//        String email = companyDto.email();
//        List<Offer> offers = new ArrayList<>();
//        for(OfferResponseDto offerResponse : offerResponseDtos) {
//            Offer offer = Offer.builder()
//                    .title(offerResponse.title())
//                    .description(offerResponse.description())
//                    .company(company)
//                    .date_created(offerResponse.date_create())
//                    .requirements(offerResponse.requirements())
//                    .build();
//            offers.add(offer);
//            company.setOffer(offer);
//            FormSaveOffer formSaveOffer = FormSaveOffer.builder()
//                    .title(offerResponse.title())
//                    .description(offerResponse.description())
//                    .requirements(offerResponse.requirements())
//                    .date_created(offerResponse.date_create())
//                    .emailCompany(email)
//                    .active(true)
//                    .build();
//            Mockito.when(offerService.saveOffer(formSaveOffer)).thenReturn(offerResponse);
//        }
//
//        Mockito.when(companyService.findCompanyByEmail(companyDto.email())).thenReturn(findCompany);
//        Mockito.when(offerService.findAllOffersInsideCompany(email)).thenReturn(offerResponseDtos);
//
//        mockMvc.perform(get("/v1/offers/{email}", email))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        Mockito.verify(offerService).findAllOffersInsideCompany(email);
//    }
    @Test
    void should_findAllOffer_return_200_and_list_offerResponseDto() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/java/resource/OfferListResponseDto.json");
        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerCompany, new TypeReference<List<OfferResponseDto>>(){});

        Mockito.when(offerService.findAllOffers()).thenReturn(offerResponseDtos);

        mockMvc.perform(get("/v1/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(offerResponseDtos.size()));

        Mockito.verify(offerService).findAllOffers();
    }

    @Test
    void should_findOfferByName_return_200_and_list_offerResponseDtos() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/java/resource/OfferListResponseDto.json");
        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerCompany, new TypeReference<List<OfferResponseDto>>() {
        });
        OfferDto offerDto = OfferDto.builder()
                .title(offerResponseDtos.get(2).title())
                .description(offerResponseDtos.get(2).description())
                .requirements(offerResponseDtos.get(2).requirements())
                .date_create(offerResponseDtos.get(2).date_create())
                .active(true)
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);

        Mockito.when(offerService.findOfferByTitle("BackendDeveloper")).thenReturn(Collections.singletonList(OfferMapper.offerToOfferResponseDto(offer)));

        mockMvc.perform(get("/v1/offers/title/{title}", "BackendDeveloper"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].title").value(offerDto.title()));
        Mockito.verify(offerService).findOfferByTitle("BackendDeveloper");
    }

    @Test
    void should_findOfferById_return_200_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        Company company = Company.builder()
                .id(1l)
                .full_name(companyDto.full_name())
                .email(companyDto.email())
                .password(companyDto.password())
                .build();
        CompanyResponseDto findCompany = CompanyResponseDto.builder()
                .email(companyDto.email())
                .fullName(companyDto.full_name())
                .build();
        String email = companyDto.email();


        FormSaveOffer formSaveOffer = FormSaveOffer.builder()
                .title(offerDto.title())
                .description(offerDto.description())
                .requirements(offerDto.requirements())
                .active(offerDto.active())
                .date_created(offerDto.date_create())
                .emailCompany(email)
                .build();

        company.setOffer(offer);
        offer.setCompany(company);

        Mockito.when(companyService.saveCompany(companyDto)).thenReturn(findCompany);
        Mockito.when(companyService.findCompanyByEmail(email)).thenReturn(findCompany);
        Mockito.when(offerService.saveOffer(formSaveOffer)).thenReturn(OfferMapper.offerToOfferResponseDto(offer));
        Mockito.when(offerService.findOfferById(123L)).thenReturn(OfferMapper.offerToOfferResponseDto(offer));

        mockMvc.perform(get("/v1/offers/id/{id}", 123L))
                .andExpect(status().isOk());

        Mockito.verify(offerService).findOfferById(123L);
    }

    @Test
    void should_updateOffer_ByEmailCompany_return_200_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailCompany = companyDto.email();

        FormUpdateOffer formUpdateOffer = FormUpdateOffer.builder()
                .idOffer(123L)
                .emailCompany(emailCompany)
                .title("Full stack dev")
                .description("Full stack dev...")
                .requirements("java, js, ts, js, react, angular, docker")
                .date_create(offerDto.date_create())
                .active(true)
                .build();
        String jsonUpdateOffer = objectMapper.writeValueAsString(formUpdateOffer);

        OfferResponseDto offerResponseDto = OfferResponseDto.builder()
                .title(formUpdateOffer.title())
                .description(formUpdateOffer.description())
                .requirements(formUpdateOffer.requirements())
                .date_create(formUpdateOffer.date_create())
                .build();

        Mockito.when(offerService.updateOffer(formUpdateOffer)).thenReturn(offerResponseDto);

        mockMvc.perform(put("/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdateOffer))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(offerResponseDto.title()))
                .andExpect(jsonPath("description").value(offerResponseDto.description()))
                .andExpect(jsonPath("requirements").value(offerResponseDto.requirements()));

        Mockito.verify(offerService).updateOffer(formUpdateOffer);
    }

    @Test
    void should_delete_Offer_ByEmailCompany_return_200_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailCompany = companyDto.email();

        FormDeleteOffer deleteOffer = FormDeleteOffer.builder()
                .idOffer(123l)
                .emailCompany(emailCompany)
                .build();

        Mockito.when(offerService.findOfferById(123l)).thenReturn(OfferMapper.offerToOfferResponseDto(offer));
        Mockito.when(offerService.deleteOffer(deleteOffer)).thenReturn(true);

        mockMvc.perform(delete("/v1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteOffer)))
                .andExpect(status().isOk());

        Mockito.verify(offerService).deleteOffer(deleteOffer);
    }

    @Test
    void should_deleteOffer_with_idOffer_not_exist_return_404_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailCompany = companyDto.email();

        FormDeleteOffer deleteOffer = FormDeleteOffer.builder()
                .idOffer(10l)
                .emailCompany(emailCompany)
                .build();

        Mockito.when(offerService.deleteOffer(deleteOffer)).thenReturn(false);

        mockMvc.perform(delete("/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteOffer)))
                .andExpect(status().isNotFound());

        Mockito.verify(offerService).deleteOffer(deleteOffer);
    }

}

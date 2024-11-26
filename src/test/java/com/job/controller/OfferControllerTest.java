package com.job.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.company.dto.CompanyDto;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.*;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferServiceImpl offerService;

    @Test
    void should_save_offerDto_inside_company_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/resource/OfferFormSave.json");
        FormSaveOffer formSaveOffer = objectMapper.readValue(readerOffer, FormSaveOffer.class);
        OfferResponseDto offerResponseDto = OfferResponseDto.builder()
                .title(formSaveOffer.title())
                .description(formSaveOffer.description())
                .requirements(formSaveOffer.requirements())
                .date_create(formSaveOffer.date_created())
                .build();

        String json = objectMapper.writeValueAsString(offerResponseDto);


        Mockito.when(offerService.saveOffer(formSaveOffer)).thenReturn(offerResponseDto);

        mockMvc.perform(post("/v1/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.title", formSaveOffer.title()));

        Mockito.verify(offerService).saveOffer(formSaveOffer);
    }

    @Test
    void should_findOfferByInsideCompany_return_200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        File readerCompany = new File("src/test/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailFindOffer = companyDto.email();

        File readerOfferResponse = new File("src/test/resource/OfferListResponse.json");
        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerOfferResponse, List.class.asSubclass(OfferResponseDto.class));

        Mockito.when(offerService.findAllOffersInsideCompany(emailFindOffer)).thenReturn(offerResponseDtos);

        mockMvc.perform(get("/v1/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailFindOffer))
                .andExpect(status().isOk());

        Mockito.verify(offerService).findAllOffersInsideCompany(emailFindOffer);
    }
    @Test
    void should_findAllOffer_return_200_and_list_offerResponseDto() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/resource/OfferFormSave.json");
        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerCompany, List.class.asSubclass(OfferResponseDto.class));

        Mockito.when(offerService.findAllOffers()).thenReturn(offerResponseDtos);

        mockMvc.perform(get("/v1/offer/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(offerResponseDtos.size()));

        Mockito.verify(offerService).findAllOffers();
    }

    @Test
    void should_findOfferByName_return_200_and_list_offerResponseDtos() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/resource/OfferFormSave.json");
        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerCompany, List.class.asSubclass(OfferResponseDto.class));

        Mockito.when(offerService.findOfferByTitle("Backend Developer")).thenReturn(offerResponseDtos);

        mockMvc.perform(get("/v1/offer/title")
                .contentType(MediaType.APPLICATION_JSON)
                .param("Backend Developer", offerResponseDtos.get(2).title()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title", offerResponseDtos.get(2).title()));

        Mockito.verify(offerService).findOfferByTitle("Backend Developer");
    }

    @Test
    void should_findOfferById_return_200_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerCompany = new File("src/test/resource/OfferFormSave.json");
        List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(readerCompany, List.class.asSubclass(OfferResponseDto.class));
        OfferDto offerDto = OfferDto.builder()
                .title(offerResponseDtos.get(0).title())
                .description(offerResponseDtos.get(0).description())
                .requirements(offerResponseDtos.get(0).requirements())
                .active(true)
                .date_create(offerResponseDtos.get(0).date_create())
                .build();
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        Mockito.when(offerService.findOfferById(123L)).thenReturn(OfferMapper.offerToOfferResponseDto(offer));

        mockMvc.perform(get("/v1/offer/123"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title", offerDto.title()));

        Mockito.verify(offerService).findOfferById(123L);
    }

    @Test
    void should_updateOffer_ByEmailCompany_return_200_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/resource/OfferFormSave.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailCompany = companyDto.email();

        FormUpdateOffer formUpdateOffer = FormUpdateOffer.builder()
                .idOffer(1L)
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

        mockMvc.perform(put("/v1/offer/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdateOffer))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title", formUpdateOffer.title()))
                .andExpect((ResultMatcher) jsonPath("$.description", formUpdateOffer.description()));

        Mockito.verify(offerService).updateOffer(formUpdateOffer);
    }

    @Test
    void should_delete_Offer_ByEmailCompany_return_200_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/resource/OfferFormSave.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailCompany = companyDto.email();

        FormDeleteOffer deleteOffer = FormDeleteOffer.builder()
                .idOffer(123l)
                .emailCompany(emailCompany)
                .build();

        Mockito.when(offerService.deleteOffer(deleteOffer)).thenReturn(true);

        mockMvc.perform(delete("/v1/offer/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(offerService).deleteOffer(deleteOffer);
    }

    @Test
    void should_deleteOffer_with_idOffer_not_exist_return_404_and_offerResponseDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/resource/OfferFormSave.json");
        OfferDto offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        File readerCompany = new File("src/test/resource/CompanyDtoFakeData.json");
        CompanyDto companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        String emailCompany = companyDto.email();

        FormDeleteOffer deleteOffer = FormDeleteOffer.builder()
                .idOffer(123l)
                .emailCompany(emailCompany)
                .build();

        Mockito.when(offerService.deleteOffer(deleteOffer)).thenReturn(false);

        mockMvc.perform(delete("/v1/offer/1234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(offerService).deleteOffer(deleteOffer);
    }

}

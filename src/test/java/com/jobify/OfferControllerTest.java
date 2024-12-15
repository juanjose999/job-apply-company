package com.jobify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobify.authentication.CustomAuthenticationProvider;
import com.jobify.offer.controller.OfferController;
import com.jobify.company.entity.Company;
import com.jobify.company.dto.CompanyDto;
import com.jobify.company.dto.CompanyResponseDto;
import com.jobify.offer.dto.*;
import com.jobify.offer.entity.Offer;
import com.jobify.company.service.CompanyServiceImpl;
import com.jobify.offer.service.OfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferServiceImpl offerService;

    @MockBean
    private CompanyServiceImpl companyService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;

    private ObjectMapper objectMapper;
    private OfferDto offerDto;
    private FormSaveOffer formSaveOffer;
    private OfferResponseDto offerResponseDto;

    private List<OfferResponseDto> offerResponseDtos;
    private CompanyDto companyDto;
    private Company company;
    private CompanyResponseDto companyResponseDto;

    @BeforeEach
    void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        File readerOffer = new File("src/test/java/resource/OfferDtoFakeData.json");
        offerDto = objectMapper.readValue(readerOffer, OfferDto.class);
        formSaveOffer = FormSaveOffer.builder()
                .title(offerDto.title())
                .description(offerDto.description())
                .requirements(offerDto.requirements())
                .active(offerDto.active())
                .emailCompany("hola@gmail.com")
                .build();
        offerResponseDto = OfferResponseDto.builder()
                .title(formSaveOffer.title())
                .description(formSaveOffer.description())
                .requirements(formSaveOffer.requirements())
                .date_create(String.valueOf(LocalDateTime.now()))
                .build();

        File readerOfferResponse = new File("src/test/java/resource/OfferListResponseDto.json");
        offerResponseDtos = objectMapper.readValue(readerOfferResponse, new TypeReference<List<OfferResponseDto>>() {});

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        companyDto = objectMapper.readValue(readerCompany, CompanyDto.class);
        company = Company.builder()
                .id(1l)
                .full_name(companyDto.full_name())
                .email(companyDto.email())
                .password(companyDto.password())
                .build();

        companyResponseDto = CompanyResponseDto.builder()
                .email(companyDto.email())
                .fullName(companyDto.full_name())
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_save_offerDto_inside_company_return_200() throws Exception {
        String jsonSaved = objectMapper.writeValueAsString(formSaveOffer);

        Mockito.when(offerService.saveOffer(formSaveOffer)).thenReturn(offerResponseDto);
        mockMvc.perform(post("/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSaved)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(offerDto.title()))
                .andExpect(jsonPath("$.description").value(offerDto.description()))
                .andExpect(jsonPath("$.requirements").value(offerDto.requirements()));

        Mockito.verify(offerService).saveOffer(formSaveOffer);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_findOfferByInsideCompany_return_200() throws Exception {
        String emailCompany = companyDto.email();

        Mockito.when(offerService.findAllOffersInsideCompany(emailCompany)).thenReturn(Collections.singletonList(offerResponseDto));
        mockMvc.perform(get("/v1/offers/email/{emailCompany}", emailCompany)
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailCompany)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(offerService).findAllOffersInsideCompany(emailCompany);
    }
//    @Test
//    void should_findAllOffer_return_200_and_list_offerResponseDto() throws Exception {
//        Mockito.when(offerService.findAllOffers()).thenReturn(offerResponseDtos);
//        mockMvc.perform(get("/v1/offers"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(offerResponseDtos.size()));
//
//        Mockito.verify(offerService).findAllOffers();
//    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_findOfferByName_return_200_and_list_offerResponseDtos() throws Exception {
        OfferDto offerDto = OfferDto.builder()
                .title(offerResponseDtos.get(2).title())
                .description(offerResponseDtos.get(2).description())
                .requirements(offerResponseDtos.get(2).requirements())
                .date_create(offerResponseDtos.get(2).date_create())
                .active(true)
                .build();

        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        List<OfferResponseDto> dtoList = Collections.singletonList(OfferMapper.offerToOfferResponseDto(offer));

        String nameOffer = "Backend Developer";
        String encodedTitle = URLEncoder.encode(nameOffer, StandardCharsets.UTF_8);

        Mockito.when(offerService.findOfferByTitle(encodedTitle)).thenReturn(dtoList);
        mockMvc.perform(get("/v1/offers/title/{title}", encodedTitle).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].title").value(offerDto.title()));
        Mockito.verify(offerService).findOfferByTitle(encodedTitle);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_findOfferById_return_200_and_offerResponseDto() throws Exception {
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        CompanyResponseDto findCompany = CompanyResponseDto.builder()
                .email(companyDto.email())
                .fullName(companyDto.full_name())
                .build();
        company.setOffer(offer);
        offer.setCompany(company);

        Mockito.when(offerService.findOfferById(123L)).thenReturn(OfferMapper.offerToOfferResponseDto(offer));

        mockMvc.perform(get("/v1/offers/id/{id}", 123L).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123L))
                .andExpect(jsonPath("$.title").value(offerDto.title()));

        Mockito.verify(offerService).findOfferById(123L);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_updateOffer_ByEmailCompany_return_200_and_offerResponseDto() throws Exception {
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        FormUpdateOffer formUpdateOffer = FormUpdateOffer.builder()
                .idOffer(123L)
                .emailCompany(company.getEmail())
                .title("Full stack dev")
                .description("Full stack dev rust")
                .requirements("java, js, ts, js, react, angular, docker")
                .date_create(offerDto.date_create())
                .active(true)
                .build();

        OfferResponseDto offerResponseDto = OfferResponseDto.builder()
                .title(formUpdateOffer.title())
                .description(formUpdateOffer.description())
                .requirements(formUpdateOffer.requirements())
                .date_create(formUpdateOffer.date_create())
                .build();

        Mockito.when(offerService.updateOffer(formUpdateOffer)).thenReturn(offerResponseDto);

        mockMvc.perform(put("/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formUpdateOffer)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(offerResponseDto.title()))
                .andExpect(jsonPath("description").value(offerResponseDto.description()))
                .andExpect(jsonPath("requirements").value(offerResponseDto.requirements()));

        Mockito.verify(offerService).updateOffer(formUpdateOffer);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_delete_Offer_ByEmailCompany_return_200_and_offerResponseDto() throws Exception {
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        FormDeleteOffer deleteOffer = FormDeleteOffer.builder()
                .idOffer(123l)
                .emailCompany(companyDto.email())
                .build();

        Mockito.when(offerService.findOfferById(123l)).thenReturn(OfferMapper.offerToOfferResponseDto(offer));
        Mockito.when(offerService.deleteOffer(deleteOffer)).thenReturn(true);

        mockMvc.perform(delete("/v1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteOffer))
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(offerService).deleteOffer(deleteOffer);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void should_deleteOffer_with_idOffer_not_exist_return_404_and_offerResponseDto() throws Exception {
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(123L);

        FormDeleteOffer deleteOffer = FormDeleteOffer.builder()
                .idOffer(10l)
                .emailCompany(companyDto.email())
                .build();

        Mockito.when(offerService.deleteOffer(deleteOffer)).thenReturn(false);

        mockMvc.perform(delete("/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteOffer))
                        .with(csrf()))
                .andExpect(status().isNotFound());

        Mockito.verify(offerService).deleteOffer(deleteOffer);
    }

}

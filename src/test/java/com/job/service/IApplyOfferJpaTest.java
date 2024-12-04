package com.job.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.apply.OfferApplyUser;
import com.job.entities.apply.dto.FormResponseApplyOffer;
import com.job.entities.apply.dto.FormUserApplyOffer;
import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.OfferDto;
import com.job.entities.offer.dto.OfferMapper;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.exception.exceptions.MyUserNotFoundException;
import com.job.exception.exceptions.OfferIsDesactiveException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.repository.apply.ApplyOfferImpl;
import com.job.repository.myuser.MyUserRepositoryImpl;
import com.job.repository.offer.OfferRepositoryImpl;
import com.job.service.myuser.MyUserServiceImpl;
import com.job.service.offer.OfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IApplyOfferJpaTest {

    @Mock
    private OfferRepositoryImpl offerRepository;

    @Mock
    private ApplyOfferImpl applyOffer;

    @Mock
    private MyUserRepositoryImpl userRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    @InjectMocks
    private MyUserServiceImpl myUserService;

    @Test
    public void should_myUser_apply_to_offer_return_formResponseApply() throws IOException, MyUserNotFoundException, OfferNotFoundException, OfferIsDesactiveException, InstantiationException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(readerUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);

        File readerOfferDto = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readerOfferDto, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(1l);

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        Company company = objectMapper.readValue(readerCompany, Company.class);
        company.setOffer(offer);
        offer.setCompany(company);

        FormResponseApplyOffer responseApplyOffer = FormResponseApplyOffer.builder()
                .name_offer("java dev")
                .date_apply(String.valueOf(LocalDateTime.now()))
                .state("PENDING")
                .build();

        when(offerRepository.findOfferById(any(Long.class))).thenReturn(Optional.of(offer));
        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(userRepository.myUserApplyOffer(any(MyUser.class), any(Offer.class))).thenReturn(OfferApplyUser.class.newInstance());
        FormResponseApplyOffer formResponseApply = myUserService.userApplyOffer(new FormUserApplyOffer("carlos@gmail.com",1l));

        assertEquals("java dev",formResponseApply.name_offer());
    }

    @Test
    public void should_myUser_apply_to_offerNotActive_return_exception() throws IOException, MyUserNotFoundException, OfferNotFoundException, OfferIsDesactiveException {
        ObjectMapper objectMapper = new ObjectMapper();
        File readerUser = new File("src/test/java/resource/UserDtoSingleFakeData.json");
        MyUserDto userDto = objectMapper.readValue(readerUser, MyUserDto.class);
        MyUser user = MyUserMapper.UserDtoToUser(userDto);

        File readerOfferDto = new File("src/test/java/resource/OfferDtoFakeData.json");
        OfferDto offerDto = objectMapper.readValue(readerOfferDto, OfferDto.class);
        Offer offer = OfferMapper.offerDtoToOffer(offerDto);
        offer.setId(1l);
        offer.setActive(false);

        File readerCompany = new File("src/test/java/resource/CompanyDtoFakeData.json");
        Company company = objectMapper.readValue(readerCompany, Company.class);
        company.setOffer(offer);
        offer.setCompany(company);

        when(offerRepository.findOfferById(any(Long.class))).thenReturn(Optional.of(offer));
        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.of(user));

        OfferIsDesactiveException isDesactiveException = assertThrows(OfferIsDesactiveException.class,
                () -> myUserService.userApplyOffer(new FormUserApplyOffer("carlos@gmail.com", 1L))
        );

        assertThrows(isDesactiveException.getClass(), () -> {
            myUserService.userApplyOffer(new FormUserApplyOffer("carlos@gmail.com", 1L));
        });
        assertEquals("this offer not available", isDesactiveException.getMessage());
    }
}

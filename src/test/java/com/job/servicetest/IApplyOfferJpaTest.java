package com.job.servicetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.offer.dto.OfferDto;
import com.job.entities.offer.dto.OfferMapper;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.exception.MyUserNotFoundException;
import com.job.exception.OfferIsDesactiveException;
import com.job.exception.OfferNotFoundException;
import com.job.repository.ApplyOfferImpl;
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
    public void should_myUser_apply_to_offer_return_formResponseApply() throws IOException, MyUserNotFoundException, OfferNotFoundException, OfferIsDesactiveException {
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

        when(offerRepository.findOfferById(any(Long.class))).thenReturn(Optional.of(offer));
        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(userRepository.myUserApplyOffer(any(MyUser.class), any(Offer.class))).thenReturn("Apply successfully");
        String formResponseApply = myUserService.userApplyOffer("carlos@gmail.com",1l);

        assertEquals("Apply successfully",formResponseApply);
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
                () -> myUserService.userApplyOffer("carlos@gmail.com", 1L)
        );

        assertThrows(isDesactiveException.getClass(), () -> {
            myUserService.userApplyOffer("carlos@gmail.com", 1L);
        });
        assertEquals("this offer not available", isDesactiveException.getMessage());
    }
}

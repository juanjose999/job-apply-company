package com.job;

import com.job.entities.Company;
import com.job.entities.Offert;
import com.job.repository.company.CompanyRepository;
import com.job.repository.company.CompanyRepositoryImpl;
import com.job.repository.offer.OfferRepository;
import com.job.repository.offer.OfferRepositoryImpl;
import com.job.service.dto.company.CompanyMapper;
import com.job.service.dto.company.CompanyResponseDto;
import com.job.service.dto.offer.FormSaveOfferInsideCompany;
import com.job.service.dto.offer.LoginFormOffer;
import com.job.service.dto.offer.OfferMapper;
import com.job.service.dto.offer.OfferResponseDto;
import com.job.service.offer.OfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferTest {

    @Mock
    public CompanyRepositoryImpl companyRepository;

    @Mock
    public OfferRepositoryImpl offerRepository;

    @InjectMocks
    public OfferServiceImpl offerService;

    @Test
    public void should_save_offer_return_offerResponseDto(){
        //que tengo que construir
        LoginFormOffer loginFormOffer = LoginFormOffer.builder()
                .title("Software development")
                .description("Software development description for area of software")
                .skills("java, spring, mysql")
                .is_active(true)
                .date_created(String.valueOf(LocalDate.now()))
                .build();
        Offert offert = OfferMapper.OfferFormLoginToEntity(loginFormOffer);
        // voy hacer las acciones
        when(offerRepository.saveOffert(any(Offert.class))).thenReturn(offert);
        OfferResponseDto offerResponseDto = offerService.saveOffert(loginFormOffer);

        assertThat(offerResponseDto).isNotNull();
        assertThat(offerResponseDto.title()).isEqualTo(loginFormOffer.title());
        assertThat(offerResponseDto.description()).isEqualTo(loginFormOffer.description());
        assertThat(offerResponseDto.skills()).isEqualTo(loginFormOffer.skills());
    }

    @Test
    public void should_saveOffer_inside_company_return_OfferResponseDto(){
        // Simulamos la entidad Company que se va a encontrar por el email
        Company company = Company.builder()
                .id(1L)
                .name_company("evil corp")
                .password("evilcorp123")
                .email("evilcorp@gmail.com")
                .offerList(new ArrayList<>()) // Lista vacía inicial
                .build();

        // Definimos la oferta que queremos añadir
        LoginFormOffer loginFormOffer = LoginFormOffer.builder()
                .title("Software development")
                .description("Software development description for area of software")
                .skills("java, spring, mysql")
                .is_active(true)
                .date_created(String.valueOf(LocalDate.now()))
                .build();

        FormSaveOfferInsideCompany formSaveOfferInsideCompany = FormSaveOfferInsideCompany.builder()
                .emailCompany("evilcorp@gmail.com")
                .formOffer(loginFormOffer)
                .build();

        // Simulamos que el repositorio encuentra la empresa por su email
        when(companyRepository.findByEmail("evilcorp@gmail.com")).thenReturn(company);

        // Simulamos que se guarda la oferta en el repositorio
        Offert savedOffer = OfferMapper.OfferFormLoginToEntity(loginFormOffer);
        when(offerRepository.saveOffert(any(Offert.class))).thenReturn(savedOffer);

        // Actuar - llamar al servicio para guardar la oferta dentro de la empresa
        OfferResponseDto offerResponseDto = offerService.saveOffertInsideCompany(formSaveOfferInsideCompany);

        // Aserciones para verificar que el resultado no es nulo y es correcto
        assertThat(offerResponseDto).isNotNull();
        assertThat(offerResponseDto.companyList().get(0)).isEqualTo(company);
        assertThat(offerResponseDto.companyList().get(0).getEmail()).isEqualTo(company.getEmail());


        assertThat(company).isNotNull();
        assertThat(company.getOfferList().get(0).getTitle()).isEqualTo(offerResponseDto.title());
        assertThat(company.getOfferList().get(0).getDescription()).isEqualTo(savedOffer.getDescription());
    }

     @Test
    public void should_findById_return_OfferResponseDto(){
         LoginFormOffer loginFormOffer = LoginFormOffer.builder()
                 .title("Software development")
                 .description("Software development description for area of software")
                 .skills("java, spring, mysql")
                 .is_active(true)
                 .date_created(String.valueOf(LocalDate.now()))
                 .build();
         Offert offert = OfferMapper.OfferFormLoginToEntity(loginFormOffer);

         when(offerRepository.saveOffert(any(Offert.class))).thenReturn(offert);
         Offert savedOfferRepo = offerRepository.saveOffert(offert);
         OfferResponseDto offerServiceSave = offerService.saveOffert(loginFormOffer);

         assertThat(savedOfferRepo).isNotNull();
         assertThat(savedOfferRepo.getTitle()).isEqualTo(loginFormOffer.title());
         assertThat(savedOfferRepo.getDescription()).isEqualTo(loginFormOffer.description());

         assertThat(offerServiceSave).isNotNull();
         assertThat(offerServiceSave.title()).isEqualTo(loginFormOffer.title());
         assertThat(offerServiceSave.description()).isEqualTo(loginFormOffer.description());
         assertThat(offerServiceSave.skills()).isEqualTo(loginFormOffer.skills());

    }

    @Test
    public void should_findOfferByIdNotExist_return_throws(){
        //que tengo que recibir
        Long id = 100L;

        when(offerRepository.getOffertById(id)).thenThrow(new RuntimeException("Offer not found"));

        assertThrows(RuntimeException.class, () -> {
            offerRepository.getOffertById(id);
        });
    }

    @Test
    public void should_updateOffer_return_OfferResponseDto(){
        Long id = 10L;
        LoginFormOffer loginFormOffer = LoginFormOffer.builder()
                .title("Software development")
                .description("Software development description for area of software")
                .skills("java, spring, mysql")
                .is_active(true)
                .date_created(String.valueOf(LocalDate.now()))
                .build();
        Offert offert = OfferMapper.OfferFormLoginToEntity(loginFormOffer);
        offert.setId(id);

        when(offerRepository.updateOffert(eq(id), any(Offert.class))).thenReturn(offert);
        OfferResponseDto offerResponseDto = offerService.updateOffert(id, loginFormOffer);

        assertThat(offerResponseDto).isNotNull();
        assertThat(offerResponseDto.title()).isEqualTo(loginFormOffer.title());
        assertThat(offerResponseDto.description()).isEqualTo(loginFormOffer.description());
    }


    @Test
    public void should_deleteOffer_return_true(){
        LoginFormOffer loginFormOffer = LoginFormOffer.builder()
                .title("Software development")
                .description("Software development description for area of software")
                .skills("java, spring, mysql")
                .is_active(true)
                .date_created(String.valueOf(LocalDate.now()))
                .build();
        Offert offert = OfferMapper.OfferFormLoginToEntity(loginFormOffer);
        offert.setId(1L);

        when(offerRepository.deleteOffert(eq(1L))).thenReturn(true);
        assertThat(offerService.deleteOffert(1L)).isTrue();


        when(offerRepository.deleteOffert(eq(1L))).thenReturn(false);
        assertThat(offerService.deleteOffert(1L)).isFalse();

    }

}

package com.job;

import com.job.entities.Apply;
import com.job.entities.Company;
import com.job.entities.MyUser;
import com.job.entities.Offert;
import com.job.service.Apply.ApplyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class ApplyTest {

    @InjectMocks
    private ApplyService applyService;

    @Test
    public void should_save_apply_return_apply() {
        MyUser user = new MyUser(1L,"juan","23123","juan@gmail.com");
        Company company = Company.builder()
                .id(1L)
                .name_company("evil corp")
                .password("evilcorp123")
                .email("evilcorp@gmail.com")
                .offerList(new ArrayList<>()) // Lista vacía inicial
                .build();
        Offert offert = Offert.builder()
                .id(1)
                .title("")
                .build();
        Apply apply = new Apply();
    }
}

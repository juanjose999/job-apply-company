package com.job.service.dto.company;

import com.job.entities.Offert;
import lombok.Builder;
import java.util.List;
@Builder
public record CompanyResponseDto(Long id,
                                 String nameCompany,
                                 String password,
                                 String email,
                                 List<Offert> offertList) {
}

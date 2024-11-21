package com.job.entities.offer;

import com.job.entities.company.Company;
import com.job.entities.user.MyUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String date_created;
    private boolean active;
    @ManyToOne
    private Company company;

    private void setDate_created() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date_created = String.valueOf(LocalDateTime.now().format(formatter));
    }

    private void setActive() {
        this.active = true;
    }
}

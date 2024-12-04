package com.job.entities.offer;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.company.Company;
import com.job.entities.user.MyUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "title is invalid")
    private String title;
    @NotBlank(message = "description is invalid")
    private String description;
    @NotBlank(message = "requirements is invalid")
    private String requirements;
    private String date_created;
    private boolean active;
    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferApplyUser> userOffers;

    public void setDate_created() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date_created = String.valueOf(LocalDateTime.now().format(formatter));
    }

    private void setActive() {
        this.active = true;
    }
}

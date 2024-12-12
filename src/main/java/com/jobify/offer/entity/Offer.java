package com.jobify.offer.entity;

import com.jobify.offer_user.entity.OfferApplyUser;
import com.jobify.company.entity.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @NotBlank(message = "Title offer cannot be empty, please fill out this field")
    private String title;
    @NotBlank(message = "Description cannot be empty, please fill out this field")
    private String description;
    @NotBlank(message = "Requirements cannot be empty, please fill out this field")
    private String requirements;
    private String date_created;
    private boolean isActive;
    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "offer")
    private Set<OfferApplyUser> userOffers;

    public void setDate_created() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date_created = String.valueOf(LocalDateTime.now().format(formatter));
    }

    private void setIsActive() {
        this.isActive = true;
    }
}

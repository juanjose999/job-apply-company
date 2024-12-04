package com.job.entities.user;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.offer.Offer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "first name is empty")
    private String first_name;
    @NotBlank(message = "last name is empty")
    private String last_name;
    @Column(unique = true)
    @Email(message = "email is empty")
    private String email;
    @NotBlank(message = "password is empty")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OfferApplyUser> userOffer = new ArrayList<>();
}

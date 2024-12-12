package com.jobify.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobify.offer_user.entity.OfferApplyUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
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
    @NotBlank(message = "First name cannot be empty, please fill out this field")
    private String first_name;
    @NotBlank(message = "Last name cannot be empty, please fill out this field")
    private String last_name;
    @Column(unique = true)
    @Email(message = "Email cannot be empty, please fill out this field")
    private String email;

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password cannot be empty, please fill out this field")
    private String password;

    private String urlImgProfile;

    private String biography;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OfferApplyUser> userOffer = new HashSet<>();
}

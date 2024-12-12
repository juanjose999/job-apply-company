package com.jobify.company.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobify.offer.entity.Offer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be empty, please fill out this field")
    private String full_name;
    private String linkImgProfile;
    @Column(unique = true)
    @Email(message = "Email is invalid")
    @NotNull(message = "Email cannot be empty, please fill out this field")
    private String email;
    @NotBlank(message = "Password cannot be empty, please fill out this field")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Offer> offer;

    public void setOffer(Offer offer){
        if(this.offer == null || this.offer.isEmpty()){
            this.offer = new HashSet<>();
        }
        this.offer.add(offer);
    }
}

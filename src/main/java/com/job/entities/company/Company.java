package com.job.entities.company;

import com.job.entities.offer.Offer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @NotBlank(message = "name not empty")
    private String full_name;
    @Email(message = "Email is invalid")
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotBlank(message = "password not empty")
    private String password;
    @OneToMany
    private List<Offer> offer;

    public void setOffer(Offer offer){
        if(this.offer == null || this.offer.isEmpty()){
            this.offer = new ArrayList<>();
        }
        this.offer.add(offer);
    }
}

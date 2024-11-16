package com.job.entities.company;

import com.job.entities.offer.Offer;
import jakarta.persistence.*;
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
    private String full_name;
    private String email;
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

package com.job.entities;

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
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name_company;
    private String password;
    private String email;
    @ManyToMany(mappedBy = "companyList", cascade = CascadeType.ALL)
    private List<Offert> offerList = new ArrayList<>();

    public void setOfferList(Offert offerList) {
        if(offerList == null) {
            this.offerList = new ArrayList<>();
        }
        this.offerList.add(offerList);
    }
}

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
@Table(name = "offert")
public class Offert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String skills;
    private String date_created;
    private Boolean is_active;
    @ManyToMany
    @JoinTable(
            name = "offert_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companyList = new ArrayList<>();

    public void setCompanyList(Company companyList) {
        if(this.companyList == null) {
            this.companyList = new ArrayList<>();
        }
        this.companyList.add(companyList);
    }
}

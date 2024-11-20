package com.job.entities.offer;

import com.job.entities.company.Company;
import com.job.entities.user.MyUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @ManyToMany
    @JoinTable(
            name = "apply_offer",
            joinColumns = @JoinColumn(name ="offer_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id")
    )
    private List<MyUser> user;
}

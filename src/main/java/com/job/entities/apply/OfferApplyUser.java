package com.job.entities.apply;

import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "apply")
public class OfferApplyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    private String date_apply;
    @Enumerated(EnumType.STRING)
    private StatusOffer status;

}

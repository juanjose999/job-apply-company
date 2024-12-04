package com.job.repository.apply;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.apply.StatusOffer;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import com.job.exception.exceptions.OfferNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApplyOfferImpl implements IApplyOffer{

    private final IApplyOfferJpa applyOfferJpa;



    @Override
    public OfferApplyUser userApplyOffer(MyUser user, Offer offer) {
        OfferApplyUser offerApplyUser = OfferApplyUser.builder()
                .user(user)
                .offer(offer)
                .date_apply(setDateApply())
                .status(StatusOffer.PENDING)
                .build();
        return applyOfferJpa.save(offerApplyUser);
    }

    @Override
    public Optional<OfferApplyUser> findOfferApplyByIdOffer(Long id) throws OfferNotFoundException {
        return Optional.of(applyOfferJpa.findById(id)).orElseThrow(() -> new OfferNotFoundException("Offer not found"));
    }

    private String setDateApply(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(formatter);
    }
}

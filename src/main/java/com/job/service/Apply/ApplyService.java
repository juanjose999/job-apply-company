package com.job.service.Apply;

import com.job.entities.Apply;
import com.job.entities.MyUser;
import com.job.entities.Offert;
import com.job.repository.apply.ApplyRepository;
import com.job.repository.apply.FormApply;
import com.job.repository.offer.OfferRepository;
import com.job.repository.user.UserRepository;
import com.job.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    private Apply saveApply(FormApply formApply) {
        Optional<MyUser> findUser = userRepository.findUserByEmail(formApply.emailUser());
        if (findUser.isPresent()) {
            MyUser myUser = findUser.get();
            Offert findOffer = offerRepository.getOffertById(formApply.idOffer());
            if(findOffer != null){
                Apply apply = new Apply();
                apply.setOffert(findOffer);
                apply.setUser(myUser);
                applyRepository.save(apply);
                return apply;
            }else{
                throw new IllegalArgumentException("Offer not found");
            }
        }else{
            throw new IllegalArgumentException("User not found");
        }
    }

}

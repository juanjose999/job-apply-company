package com.job.repository.myuser;

import com.job.entities.apply.OfferApplyUser;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;

import java.util.List;
import java.util.Optional;

public interface IMyUserRepository {
    List<MyUser> allUser();
    Optional<MyUser> findUserByEmail(String email);
    MyUser saveUser(MyUser user);
    OfferApplyUser myUserApplyOffer(MyUser user, Offer offer);
    Optional<MyUser> updateUser(String email, MyUser user);
    boolean deleteUser(String email);
}

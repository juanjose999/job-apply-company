package com.jobify.user.repository;

import com.jobify.offer_user.entity.OfferApplyUser;
import com.jobify.offer.entity.Offer;
import com.jobify.user.entity.MyUser;

import java.util.List;
import java.util.Optional;

public interface IMyUserRepository {
    List<MyUser> allUser();
    Optional<MyUser> findUserByEmail(String email);
    Optional<MyUser> findUserById(Long id);
    MyUser saveUser(MyUser user);
    OfferApplyUser myUserApplyOffer(MyUser user, Offer offer);
    Optional<MyUser> updateUser(String email, MyUser user);
    boolean deleteUser(String email);
}

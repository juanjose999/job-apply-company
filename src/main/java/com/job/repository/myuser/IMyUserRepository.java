package com.job.repository.myuser;

import com.job.entities.user.MyUser;

import java.util.List;
import java.util.Optional;

public interface IMyUserRepository {
    List<MyUser> allUser();
    Optional<MyUser> findUserByEmail(String email);
    MyUser saveUser(MyUser user);
    Optional<MyUser> updateUser(String email, MyUser user);
    boolean deleteUser(String email);
}

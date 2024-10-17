package com.job.repository;

import com.job.entities.MyUser;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<MyUser> allUsers();
    Optional<MyUser> findUserByEmail(String email);
    MyUser saveUser(MyUser user);
    MyUser updateUser(Long idUserToUpdate, MyUser user);
    Boolean deleteUserById(Long idUser);
}

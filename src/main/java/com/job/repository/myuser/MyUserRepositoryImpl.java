package com.job.repository.myuser;

import com.job.entities.user.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyUserRepositoryImpl implements IMyUserRepository{

    private final IMyUserRepositoryJpa  myUserRepositoryJpa;

    @Override
    public List<MyUser> allUser() {
        return myUserRepositoryJpa.findAll();
    }

    @Override
    public Optional<MyUser> findUserByEmail(String email) {
        return myUserRepositoryJpa.findByEmail(email);
    }

    @Override
    public MyUser saveUser(MyUser user) {
        return myUserRepositoryJpa.save(user);
    }

    @Override
    public Optional<MyUser> updateUser(String email, MyUser user) {
        Optional<MyUser> findUser = myUserRepositoryJpa.findByEmail(email);
        if (findUser.isPresent()) {
            var userOToUpdate = findUser.get();
            if(user.getFirst_name()!=null) userOToUpdate.setFirst_name(user.getFirst_name());
            if(user.getLast_name()!=null) userOToUpdate.setLast_name(user.getLast_name());
            if(user.getPassword()!=null) userOToUpdate.setPassword(user.getPassword());
            return Optional.of(myUserRepositoryJpa.save(userOToUpdate));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(String email) {
        if(myUserRepositoryJpa.findByEmail(email).isPresent()) {
            myUserRepositoryJpa.deleteByEmail(email);
            return true;
        }
        return false;
    }
}

package com.job.repository.user;

import com.job.entities.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final UserRepositoryJpa userRepositoryJpa;

    @Override
    public List<MyUser> allUsers() {
        return userRepositoryJpa.findAll();
    }

    @Override
    public Optional<MyUser> findUserByEmail(String email) {
        return Optional.ofNullable(userRepositoryJpa.findByEmail(email));
    }

    @Override
    public MyUser saveUser(MyUser user) {
        return userRepositoryJpa.save(user);
    }

    @Override
    public MyUser updateUser(Long idUserToUpdate, MyUser user) {
        MyUser findUser = userRepositoryJpa.findById(idUserToUpdate)
                .orElseThrow(() -> new RuntimeException("User not found"));
        findUser.setId(findUser.getId());
        findUser.setUser_name(user.getUser_name());
        findUser.setEmail(user.getEmail());
        findUser.setPassword(user.getPassword());

        return userRepositoryJpa.save(findUser);
    }

    @Override
    public Boolean deleteUserById(Long idUser) {
        if(userRepositoryJpa.existsById(idUser)){
            userRepositoryJpa.deleteById(idUser);
            return true;
        }else{
            return false;
        }
    }
}

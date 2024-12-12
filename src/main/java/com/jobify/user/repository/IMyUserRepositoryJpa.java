package com.jobify.user.repository;

import com.jobify.user.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMyUserRepositoryJpa extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByEmail(String email);
    void deleteByEmail(String email);
}

package com.job.repository;

import com.job.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<MyUser, Long> {
    MyUser findByEmail(String email);
}

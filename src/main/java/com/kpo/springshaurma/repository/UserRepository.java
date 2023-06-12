package com.kpo.springshaurma.repository;

import com.kpo.springshaurma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    //User findByName(String username);

    boolean existsByEmail(String email);
}
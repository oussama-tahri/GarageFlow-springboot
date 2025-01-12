package com.tahrioussama.garage_renault.security.repositories;

import com.tahrioussama.garage_renault.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

package com.clothes_store.repository;

import com.clothes_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

//    Optional<User> findByUsernameOrEmail(String username, String email);

//    Optional<User> findByUsername(String username);

//    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    Optional<User> findById(Long userId);
}

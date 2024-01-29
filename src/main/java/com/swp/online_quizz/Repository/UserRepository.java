package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}

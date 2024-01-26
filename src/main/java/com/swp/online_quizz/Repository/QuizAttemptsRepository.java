package com.swp.online_quizz.Repository;


import com.swp.online_quizz.Entity.QuizAttempts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptsRepository extends JpaRepository<QuizAttempts, Integer> {
    List<QuizAttempts> findByquizId(Integer quizId);

}

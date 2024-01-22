package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizProgressRepository extends JpaRepository<QuizProgress, Long> {
    List<QuizProgress> findByQuizId(Long quizId);
}

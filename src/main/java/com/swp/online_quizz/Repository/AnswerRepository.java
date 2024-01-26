package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}

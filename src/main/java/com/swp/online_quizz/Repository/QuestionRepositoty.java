package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepositoty extends JpaRepository<Question, Integer> {
}

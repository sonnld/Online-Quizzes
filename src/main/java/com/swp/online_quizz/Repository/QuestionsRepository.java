package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions,Integer> {
}

package com.swp.online_quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.Question;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Integer> {
}
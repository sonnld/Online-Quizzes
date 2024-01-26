package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Answers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswersRepository extends JpaRepository<Answers, Integer> {

    List<Answers> findByquestionId(Integer questionId);
}

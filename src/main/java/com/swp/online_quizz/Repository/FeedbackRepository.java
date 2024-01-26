package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByattemptId(Integer attemptId);
}

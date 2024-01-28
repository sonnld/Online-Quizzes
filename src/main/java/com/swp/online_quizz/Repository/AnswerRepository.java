package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Answer a SET a.answerContent = :newAnswerContent, a.isCorrect = :newIsCorrect WHERE a.answerId = :answerId")
    void updateAnswerByAnswerId(
            @Param("answerId") Integer answerId,
            @Param("newAnswerContent") String newAnswerContent,
            @Param("newIsCorrect") Boolean newIsCorrect
    );

    void deleteByQuestion_QuestionId(Integer questionId);
}

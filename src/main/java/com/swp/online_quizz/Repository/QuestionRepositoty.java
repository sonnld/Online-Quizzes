package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepositoty extends JpaRepository<Question, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Question q SET q.questionContent = :newQuestionContent, q.questionType = :newQuestionType, q.imageURL = :newImageURL, q.videoURL = :newVideoURL WHERE q.questionId = :questionId")
    void updateQuestionByQuestionId(
            @Param("questionId") Integer questionId,
            @Param("newQuestionContent") String newQuestionContent,
            @Param("newQuestionType") String newQuestionType,
            @Param("newImageURL") String newImageURL,
            @Param("newVideoURL") String newVideoURL
    );

    @Modifying
    @Query("DELETE FROM Question q WHERE q.questionId = :questionId")
    void deleteQuestionById(@Param("questionId") Integer questionId);
}

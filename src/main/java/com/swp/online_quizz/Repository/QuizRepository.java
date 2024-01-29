package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quiz;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Quiz q SET q.quizName = :newQuizName, q.timeLimit = :newTimeLimit," +
            " q.isCompleted = :newIsCompleted WHERE q.quizId = :quizId")
    void updateQuizByQuizId(@Param("quizId") Integer quizId,
                              @Param("newQuizName") String newQuizName,
                              @Param("newTimeLimit") Integer newTimeLimit,
                              @Param("newIsCompleted") Boolean newIsCompleted);
    Optional<Quiz> findByQuizId(Integer quizID);
    Optional<Quiz> deleteQuizByQuizId(Integer quizId);
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Quiz q WHERE q.quizId = :quizId")
//    void deleteQuizById(@Param("quizId") Integer quizId);
}

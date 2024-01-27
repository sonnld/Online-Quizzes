package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quiz;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Quiz q SET q.quizName = :newQuizName, q.timeLimit = :newTimeLimit," +
            " q.isCompleted = :newIsCompleted WHERE q.quizName = :quizName")
    void updateQuizByQuizName(@Param("quizName") String quizName,
                              @Param("newQuizName") String newQuizName,
                              @Param("newTimeLimit") Integer newTimeLimit,
                              @Param("newIsCompleted") Boolean newIsCompleted);
    Optional<Quiz> findByQuizName(String quizName);
}

package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IQuizService {
    List<Quiz> getALl();

    Quiz createQuiz(String quizName, Integer timeLimit, String subject, Integer teacherId);


    Optional<Quiz> updateQuizByQuizId(Integer quizId, String newQuizName,
                                      Integer newTimeLimit, Boolean newIsCompleted);

    @Transactional
    void deleteQuizById(Integer quizId);
}

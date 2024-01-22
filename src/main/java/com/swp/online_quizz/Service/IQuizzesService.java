package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quizzes;


import java.util.List;

public interface IQuizzesService {
    List<Quizzes> getAllQuizzes();
    Quizzes getQuizById(Integer quizId);
    boolean createQuiz(Quizzes quiz);
    Quizzes updateQuiz(Integer quizId, Quizzes quiz);
    boolean deleteQuiz(Integer quizId);
}

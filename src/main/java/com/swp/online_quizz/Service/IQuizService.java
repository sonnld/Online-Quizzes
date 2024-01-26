package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface IQuizService {
    List<Quiz> getALl();

    Quiz createQuiz(String quizName, Integer timeLimit, Subject subject, Integer teacherId, List<Question> questions);
}

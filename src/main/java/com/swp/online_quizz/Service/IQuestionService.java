package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Question;

public interface IQuestionService {




     Question createQuestion(Question question, Integer quizId);

     public Question getQuestionById(Integer questionId);
}

package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;

import java.util.List;

public interface IAnswerService {
    List<Answer> getALl();
    Answer createAnswer(String answerContent, Integer questionId,boolean isCorrect);

    Answer updateAnswer(Integer answerId, Answer updatedAnswer);
}

package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAnswerService {
    List<Answer> getALl();
    Answer createAnswer(String answerContent, Integer questionId,boolean isCorrect);


    @Transactional
    void updateAnswer(Integer answerId, String newAnswerContent, Boolean newIsCorrect);

    Boolean updateAnswer(Integer id, Answer answer);
}

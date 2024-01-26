package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;

public interface IAnswerService {
    Answer createAnswer(Answer answer, Integer questionId);
}

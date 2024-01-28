package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService implements IAnswerService{
    @Autowired
    AnswersRepository answersRepository;
    @Override
    public Answer getAnswers(Integer answerId) {
        return answersRepository.getReferenceById(answerId);
    }
}
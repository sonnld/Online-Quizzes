package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerSevice implements IAnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;
    @Override
    public Answer createAnswer(Answer answer, Integer questionId) {
        Question existingQuestion = questionService.getQuestionById(questionId);
        answer.setQuestion(existingQuestion);
        return answerRepository.save(answer);
    }
}

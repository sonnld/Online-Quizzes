package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerSevice implements IAnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;
    @Override
    public List<Answer> getALl() {
        return answerRepository.findAll();
    }
    @Override
    public Answer createAnswer(String answerContent, Integer questionId,boolean isCorrect) {
        Question existingQuestion = questionService.getQuestionById(questionId);
        Answer answer = new Answer(answerContent,true);
        answer.setQuestion(existingQuestion);
        return answerRepository.save(answer);
    }
    @Override
    @Transactional

    public void updateAnswer(Integer answerId, String newAnswerContent, Boolean newIsCorrect) {
        // Check if the answer exists
        Answer existingAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + answerId));

        // Update the attributes
        existingAnswer.setAnswerContent(newAnswerContent);
        existingAnswer.setIsCorrect(newIsCorrect);

        // Save the updated answer
        answerRepository.save(existingAnswer);
    }
    @Override
    public Boolean updateAnswer(Integer id, Answer answer) {
        try {
            Answer uAnswer = answerRepository.getReferenceById(id);
            uAnswer.setAnswerContent(answer.getAnswerContent());
            uAnswer.setIsCorrect(answer.getIsCorrect());
            this.answerRepository.save(uAnswer);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

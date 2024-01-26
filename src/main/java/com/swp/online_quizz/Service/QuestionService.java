package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Repository.QuestionRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    public QuestionRepositoty questionRepositoty;
    @Autowired
    @Lazy
    private QuizService quizService;
    @Override

    public Question createQuestion(Question question, Integer quizId) {
        Quiz existingQuiz = quizService.getQuizById(quizId);
        question.setQuiz(existingQuiz);
        return questionRepositoty.save(question);
    }
    @Override
    public Question getQuestionById(Integer questionId) {
            return questionRepositoty.findById(questionId).orElse(null);
    }

}

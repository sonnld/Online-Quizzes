package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Repository.QuestionRepositoty;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    public QuestionRepositoty questionRepositoty;
    @Autowired
    @Lazy
    private QuizService quizService;

    @Override
    public List<Question> getALl() {
        return questionRepositoty.findAll();
    }
    @Override
    public Question createQuestion(Integer quizId, String questionContent, String questionType, String imageURL, String videoURL) {
        Quiz existingQuiz = quizService.getQuizById(quizId);
        Question question = new Question(questionContent,questionType, imageURL,videoURL);
        question.setQuizID(existingQuiz);
        return questionRepositoty.save(question);
    }
    @Override
    public Question getQuestionById(Integer questionId) {
            return questionRepositoty.findById(questionId).orElse(null);
    }
    @Override
    @Transactional

    public Question updateQuestion(Integer questionId, Question updatedQuestion) {
        Optional<Question> existingQuestion = questionRepositoty.findById(questionId);

        if (existingQuestion.isPresent()) {
            Question questionToUpdate = existingQuestion.get();
            questionToUpdate.setQuestionContent(updatedQuestion.getQuestionContent());
            questionToUpdate.setQuestionType(updatedQuestion.getQuestionType());
            questionToUpdate.setImageURL(updatedQuestion.getImageURL());
            questionToUpdate.setVideoURL(updatedQuestion.getVideoURL());

            return questionRepositoty.save(questionToUpdate);
        } else {
            throw new EntityNotFoundException("Question not found with id: " + questionId);
        }
    }
}

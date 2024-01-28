package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Repository.AnswerRepository;
import com.swp.online_quizz.Repository.QuestionRepositoty;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    public QuestionRepositoty questionRepositoty;
    @Autowired
    @Lazy
    private QuizService quizService;
    @Autowired
    public AnswerRepository answerRepository;
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
    public Question updateQuestion(Integer questionId, String newQuestionContent, String newQuestionType, String newImageURL, String newVideoURL) {
        // Check if the question exists
        Question existingQuestion = questionRepositoty.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        // Update the attributes
        existingQuestion.setQuestionContent(newQuestionContent);
        existingQuestion.setQuestionType(newQuestionType);
        existingQuestion.setImageURL(newImageURL);
        existingQuestion.setVideoURL(newVideoURL);

        // Save the updated question
        return questionRepositoty.save(existingQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestionAndAnswers(Integer questionId) {
        // First, delete associated answers
        answerRepository.deleteByQuestion_QuestionId(questionId);

        // Then, delete the question
        questionRepositoty.deleteQuestionById(questionId);
    }
}

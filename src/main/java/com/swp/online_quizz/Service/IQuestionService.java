package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IQuestionService {


    List<Question> getALl();


   // Question createQuestion(Integer quizId, String questionContent, String questionType);

    Question createQuestion(Integer quizId, String questionContent, String questionType, String imageURL, String videoURL);


    boolean createQuestion1(Question question);

    public Question getQuestionById(Integer questionId);

    @Transactional
    Question updateQuestion(Integer questionId, String newQuestionContent, String newQuestionType, String newImageURL, String newVideoURL);


    Boolean updateQuestion1(Integer id, Question question);

    List<Question> getQuestionsByQuizId(Integer quizId);

    @Transactional
    void deleteQuestionAndAnswers(Integer questionId);
}

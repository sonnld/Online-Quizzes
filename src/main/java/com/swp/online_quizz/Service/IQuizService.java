package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IQuizService {
    List<Quiz> getALl();

    Quiz createQuiz(String quizName, Integer timeLimit, String subject, Integer teacherId);


    boolean createQuiz1(Quiz quiz);

    Optional<Quiz> updateQuizByQuizId(Integer quizId, String newQuizName,
                                      Integer newTimeLimit, Boolean newIsCompleted);


    @Transactional
    Boolean updateQuizByQuizId1(Integer id, Quiz quiz);

//    @Transactional
//    Quiz updateAll(Integer quizId, String newQuizName, Integer newTimeLimit, Boolean newIsCompleted,
//                   List<Integer> questionIds, List<String> newQuestionContents, List<String> newQuestionTypes,
//                   List<String> newImageURLs, List<String> newVideoURLs,
//                   List<List<Integer>> answerIds, List<List<String>> newAnswerContents, List<List<Boolean>> newIsCorrects);

    @Transactional
    Boolean updateQuizWithQuestionsAndAnswers(Integer quizId, Quiz updatedQuiz);

    Question findQuestionById(List<Question> questions, Integer quesId);

    Answer findAnswerByAnswerId(List<Answer> answers, Integer ansId);

    @Transactional
    void deleteQuizById(Integer quizId);
}

package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService implements IQuizService{
    @Autowired
    private final QuizRepository quizRepository;
    @Autowired
    private IQuestionService iQuestionService;
    @Autowired
    @Lazy
    private SubjectService subjectService;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private QuestionService questionService;
    @Autowired
    private AnswerSevice answerSevice;
    @Override
    public List<Quiz> getALl() {
        return quizRepository.findAll();
    }
    public Quiz getQuizById(Integer quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Not found ID: " + quizId));
    }

    @Override
    public Quiz createQuiz(String quizName, Integer timeLimit, String subjectName,
                           Integer teacherId) {
        Subject existingSubject = subjectService.createOrUpdateSubject(subjectName);
        User teacher = userService.getUserById(teacherId);

        Quiz quiz = new Quiz(teacher,existingSubject,quizName,timeLimit,false);
        Quiz savedQuiz = quizRepository.save(quiz);

        return savedQuiz;
    }

    @Transactional
    @Override
    public Optional<Quiz> updateQuizByQuizId(Integer quizId, String newQuizName,
                                               Integer newTimeLimit, Boolean newIsCompleted) {
        quizRepository.updateQuizByQuizId(quizId, newQuizName, newTimeLimit, newIsCompleted);


        return quizRepository.findByQuizId(quizId);
    }

    @Transactional
    @Override
    public void deleteQuizById(Integer quizId) {
        //iQuestionService.deleteQuestionAndAnswers(quizId);
        quizRepository.deleteById(quizId);
    }
}

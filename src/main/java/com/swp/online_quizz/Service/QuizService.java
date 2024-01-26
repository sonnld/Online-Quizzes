package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class QuizService implements IQuizService{
    @Autowired
    private final QuizRepository quizRepository;
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
                .orElseThrow(() -> new RuntimeException("Bài kiểm tra không tồn tại với ID: " + quizId));
    }
    @Override
    public Quiz createQuiz(String quizName, Integer timeLimit, Subject subject, Integer teacherId, List<Question> questions) {


        Subject existingSubject = subjectService.createOrUpdateSubject(subject);
        User teacher = (User) userService.getUserById(teacherId);

        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);
        quiz.setTimeLimit(timeLimit);
        quiz.setSubject(existingSubject);
        quiz.setTeacher((com.swp.online_quizz.Entity.User) teacher);
        quiz.setIsCompleted(false);

        Quiz savedQuiz = quizRepository.save(quiz);

        for (Question question : questions) {
            question.setQuiz(savedQuiz);
            Question savedQuestion = questionService.createQuestion(question, quiz.getId());

            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {
                answer.setQuestion(savedQuestion);
                answerSevice.createAnswer(answer, question.getId());
            }
        }

        return savedQuiz;
    }




}

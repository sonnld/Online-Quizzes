package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.*;
import com.swp.online_quizz.Repository.AnswerRepository;
import com.swp.online_quizz.Repository.QuestionRepositoty;
import com.swp.online_quizz.Repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService implements IQuizService {
    @Autowired
    private final QuizRepository quizRepository;
    @Autowired
    private IQuestionService iQuestionService;
    @Autowired
    private IAnswerService iAnswerService;
    @Autowired
    @Lazy
    private SubjectService subjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionRepositoty questionRepositoty;
    @Autowired
    private AnswerRepository answerRepository;
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

        Quiz quiz = new Quiz(teacher, existingSubject, quizName, timeLimit, false);
        Quiz savedQuiz = quizRepository.save(quiz);

        return savedQuiz;
    }
    @Override
    public boolean createQuiz1(Quiz quiz) {
        try {
            Subject existingSubject = subjectService.createOrUpdateSubject(quiz.getSubject().getSubjectName());
            User teacher = userService.getUserById(quiz.getTeacher().getUserId());

            quiz.setSubject(existingSubject);
            quiz.setTeacher(teacher);
            quiz.setIsCompleted(false);

            Quiz savedQuiz = quizRepository.save(quiz);

            return true; // Nếu không có ngoại lệ, trả về true
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu cần
            return false; // Nếu có ngoại lệ, trả về false
        }
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
    public Boolean updateQuizByQuizId1(Integer id, Quiz quiz) {
        try {
            Quiz uQuiz = quizRepository.getReferenceById(id);
            uQuiz.setQuizName(quiz.getQuizName());
            uQuiz.setTimeLimit(quiz.getTimeLimit());
            this.quizRepository.save(uQuiz);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    @Transactional
    @Override
    public Quiz updateAll(Integer quizId, String newQuizName, Integer newTimeLimit, Boolean newIsCompleted,
                          List<Integer> questionId, List<String> newQuestionContent, List<String> newQuestionType,
                          List<String> newImageURL, List<String> newVideoURL,
                          List<List<Integer>> answerId, List<List<String>> newAnswerContent, List<List<Boolean>> newIsCorrect) {
        // Kiểm tra xem quiz có tồn tại không
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + quizId));

        // Cập nhật thông tin của quiz
        quiz.setQuizName(newQuizName);
        quiz.setTimeLimit(newTimeLimit);
        quiz.setIsCompleted(newIsCompleted);

        // Lấy danh sách các question trong quiz
        List<Question> existingQuestions = quiz.getQuestions();

        // Cập nhật thông tin của các question
        for (int i = 0; i < questionId.size(); i++) {
            Integer quesId = questionId.get(i);

            // Kiểm tra xem question có tồn tại trong danh sách không
            Question existingQuestion = findQuestionById(existingQuestions, quesId);

            // Cập nhật thông tin của question
            existingQuestion.setQuestionContent(newQuestionContent.get(i));
            existingQuestion.setQuestionType(newQuestionType.get(i));
            existingQuestion.setImageURL(newImageURL.get(i));
            existingQuestion.setVideoURL(newVideoURL.get(i));
            questionRepositoty.save(existingQuestion);
            // Lấy danh sách các answer trong question
            List<Answer> existingAnswers = existingQuestion.getAnswers();

            // Cập nhật thông tin của các answer
            for (int j = 0; j < answerId.get(i).size(); j++) {
                Integer ansId = answerId.get(i).get(j);

                // Kiểm tra xem answer có tồn tại trong danh sách không
               Answer existingAnswer = findAnswerByAnswerId(existingAnswers, ansId);

                // Cập nhật thông tin của answer
                existingAnswer.setAnswerContent(newAnswerContent.get(i).get(j));
                existingAnswer.setIsCorrect(newIsCorrect.get(i).get(j));
                answerRepository.save(existingAnswer);
            }
        }

        return quizRepository.save(quiz);
    }
    @Override
    public Question findQuestionById(List<Question> questions, Integer quesId) {
        for (Question question : questions) {
            if (question.getQuestionId() == quesId) {
                return question;
            }
        }
        throw new EntityNotFoundException("Question not found with id: " + quesId);
    }

    @Override
    public Answer findAnswerByAnswerId(List<Answer> answers, Integer ansId) {

        for (Answer answer : answers) {
            if (answer.getAnswerId().equals(ansId)) {
                return answer;
            }
        }
        throw new EntityNotFoundException("Answer not found with id: " + ansId);
}




    @Transactional
    @Override
    public void deleteQuizById(Integer quizId) {
        iQuestionService.deleteQuestionAndAnswers(quizId);
        quizRepository.deleteQuizByQuizId(quizId);
    }
}

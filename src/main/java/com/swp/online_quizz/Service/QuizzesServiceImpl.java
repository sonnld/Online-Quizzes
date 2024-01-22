package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Repository.QuizzesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class QuizzesServiceImpl implements IQuizzesService {
    @Autowired
    private QuizzesRepository quizzesRepository;
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private AnswersService answersService;
    @Override
    public List<Quizzes> getAllQuizzes() {
        return quizzesRepository.findAll();
    }
    @Override
    public Quizzes getQuizById(Integer quizId) {
        Optional<Quizzes> optionalQuiz = quizzesRepository.findById(quizId);
        return optionalQuiz.orElse(null);
    }
    @Override
    public boolean createQuiz(Quizzes quiz) {
        try{
            this.quizzesRepository.save(quiz);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    @Override
    public Quizzes updateQuiz(Integer id, Quizzes updatedQuiz) {
        Optional<Quizzes> existingQuiz = quizzesRepository.findById(id);

        if (existingQuiz.isPresent()) {
            Quizzes quizToUpdate = existingQuiz.get();
            quizToUpdate.setTeacherId(updatedQuiz.getTeacherId());
            quizToUpdate.setSubjectId(updatedQuiz.getSubjectId());
            quizToUpdate.setQuizName(updatedQuiz.getQuizName());
            quizToUpdate.setTimeLimit(updatedQuiz.getTimeLimit());
            quizToUpdate.setIsCompleted(updatedQuiz.getIsCompleted());


            return quizzesRepository.save(quizToUpdate);
        } else {
            throw new EntityNotFoundException("Quiz not found with id: " + id);
        }
    }
    @Override
    @Transactional
    public void deleteQuiz(Integer quizId) {
        Quizzes quiz = quizzesRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            // Xóa dữ liệu trong các bảng liên quan
            deleteRelatedData(quiz);

            // Sau đó xóa quiz
            quizzesRepository.deleteById(quizId);
        }
    }

    // Xóa dữ liệu trong các bảng liên quan
    private void deleteRelatedData(Quizzes quiz) {
        List<Questions> questions = quiz.getQuestions();
        for (Questions question : questions) {
            // Xóa dữ liệu trong các bảng liên quan đến câu hỏi
            answersService.deleteAnswersByQuesId(question.getQuestionId());
            // Add other methods to delete data in other related tables if needed
            // questionService.deleteQuestion(question.getQuestionId()); // already handled in deleteQuiz
        }

        // Add other methods to delete data in other related tables if needed
    }
}


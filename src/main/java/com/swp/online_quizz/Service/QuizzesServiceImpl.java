package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Repository.QuizzesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class QuizzesServiceImpl implements IQuizzesService {
    @Autowired
    private QuizzesRepository quizzesRepository;
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
    public boolean deleteQuiz(Integer quizId) {
        if (quizzesRepository.existsById(quizId)) {
            quizzesRepository.deleteById(quizId);
            return true;
        }
        return false;
    }
}


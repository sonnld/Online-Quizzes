package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizProgress;
import com.swp.online_quizz.Repository.QuizProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizProgressService {
    @Autowired
    private QuizProgressRepository quizProgressRepository;

    public void deleteProgressByQuizId(Integer quizId) {
        List<QuizProgress> progressList = quizProgressRepository.findByquizId(quizId);
        for (QuizProgress progress : progressList) {
            quizProgressRepository.deleteById(progress.getProgressId());
        }
    }
}

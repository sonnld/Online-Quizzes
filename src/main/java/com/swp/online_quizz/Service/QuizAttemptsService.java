package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizAttemptsService {

    @Autowired
    private QuizAttemptsRepository quizAttemptRepository;

    public void deleteAttemptsByQuizId(Long quizId) {
        List<QuizAttempts> attempts = quizAttemptRepository.findByQuizId(quizId);
        for (QuizAttempts attempt : attempts) {
            quizAttemptRepository.deleteById(attempt.getAttemptId());
        }
    }


}

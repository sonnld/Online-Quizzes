package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answers;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Repository.AnswersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswersService {
    private AnswersRepository answersRepository;
    @Transactional
    public void deleteAnswer(Integer answerId) {
        // Xóa câu trả lời
        answersRepository.deleteById(answerId);
    }
    @Transactional
    public Answers getAnswerByQuesId(Integer questionId) {
        Optional<Answers> optionalAnswers = answersRepository.findById(questionId);
        return optionalAnswers.orElse(null);
    }
}

package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answers;
import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Repository.QuestionsRepository;
import com.swp.online_quizz.Repository.QuizzesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionsService {
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswersService answersService;



    @Transactional
    public void deleteQuestion(Integer questionId) {
        // Lấy danh sách câu trả lời thuộc về câu hỏi
        List<Answers> answers = (List<Answers>) answersService.getAnswerByQuesId(questionId);

        // Xóa từng câu trả lời và các thông tin liên quan
        for (Answers answer : answers) {
            answersService.deleteAnswer(answer.getAnswerId());
        }

        // Sau đó xóa câu hỏi
        questionsRepository.deleteById(questionId);
    }
}

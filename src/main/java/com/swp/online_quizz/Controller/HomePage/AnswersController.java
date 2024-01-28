package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Service.IAnswerService;
import com.swp.online_quizz.Service.IQuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswersController {
    @Autowired
    public IAnswerService iAnswerService;
    @Autowired
    public IQuestionService iQuestionService;
    @GetMapping("/all")
    public List<Answer> getAll(){
        return iAnswerService.getALl();
    }

    @PostMapping("/create/")
    public ResponseEntity<Answer> createAnswer(@ModelAttribute Answer answer,
            @RequestParam(name="answerContent") String answerContent,
            @RequestParam(name="questionId") Integer questionId,
            @RequestParam(name="isCorrect") boolean isCorrect
    ) {
        try {
            Question existingQuestion = iQuestionService.getQuestionById(questionId);
            Answer answers = new Answer(answerContent,true);
            answers.setQuestion(existingQuestion);
            Answer createdAnswer = iAnswerService.createAnswer(answerContent,questionId,true);
            return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{answerId}")
    public ResponseEntity<Answer> updateAnswer(
            @PathVariable Integer answerId,
            @RequestBody Answer updatedAnswer) {
        try {
            Answer updated = iAnswerService.updateAnswer(answerId, updatedAnswer);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

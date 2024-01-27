package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.IQuestionService;

import com.swp.online_quizz.Service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private IQuestionService iQuestionService;
    @GetMapping("/all")
    public List<Question> getAll(){
        return iQuestionService.getALl();
    }
    @PostMapping("/create/{quizId}/{questionContent}/{questionType}/{imageURL}/{videoURL}")
    public ResponseEntity<String> createQuestion(
            @PathVariable Integer quizId,
            @PathVariable String questionContent,
            @PathVariable String questionType,
            @PathVariable(required = false) String imageURL,
            @PathVariable(required = false) String videoURL) {

        try {
            Question createdQuestion = iQuestionService.createQuestion(quizId, questionContent, questionType, imageURL, videoURL);
            return new ResponseEntity<>("Question created successfully with ID: " + createdQuestion.getQuestionId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create question. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer questionId,
                                                   @RequestBody Question updatedQuestion) {
        try {
            Question updated = iQuestionService.updateQuestion(questionId, updatedQuestion);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

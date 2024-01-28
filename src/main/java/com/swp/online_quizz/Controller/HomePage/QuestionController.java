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
    @PostMapping("/create")
    public ResponseEntity<String> createQuestion(@ModelAttribute Question question,
            @RequestParam(name="quizId") Integer quizId,
            @RequestParam(name="questionContent") String questionContent,
            @RequestParam(name="questionType") String questionType,
            @RequestParam(name="imageURL", required = false) String imageURL,
            @RequestParam(name="videoURL",required = false) String videoURL) {

        try {
            Question createdQuestion = iQuestionService.createQuestion(quizId, questionContent, questionType, imageURL, videoURL);
            return new ResponseEntity<>("Question created successfully with ID: " + createdQuestion.getQuestionId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create question. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 //   @PutMapping("/update/{questionId}")
//    public ResponseEntity<Question> updateQuestion(@PathVariable Integer questionId,
//                                                   @RequestBody Question updatedQuestion) {
//        try {
//            Question updated = iQuestionService.updateQuestion(questionId, updatedQuestion);
//            return new ResponseEntity<>(updated, HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @PutMapping("/update/{questionId}/{newQuestionContent}/{newQuestionType}/{newImageURL}/{newVideoURL}")
    public ResponseEntity<String> updateQuestion(
            @PathVariable Integer questionId,
            @PathVariable String newQuestionContent,
            @PathVariable String newQuestionType,
            @PathVariable(required = false) String newImageURL,
            @PathVariable(required = false) String newVideoURL
    ) {
        try {
            // Call the service method to update the question
            Question updatedQuestion = iQuestionService.updateQuestion(questionId, newQuestionContent, newQuestionType, newImageURL, newVideoURL);

            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Question not found with id: " + questionId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update question. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{questionId}")
    public ResponseEntity<String> deleteQuestionAndAnswers(@PathVariable Integer questionId) {
        String message;
        try {
            iQuestionService.deleteQuestionAndAnswers(questionId);
            message = "Question and answers deleted successfully!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Error deleting question and answers: " + e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

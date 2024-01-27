package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.IQuizService;
import com.swp.online_quizz.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController
{
    private final IQuizService iQuizService;

    @Autowired
    public QuizController(IQuizService iQuizService) {
        this.iQuizService = iQuizService;
    }

    @Autowired
    private QuizService quizService;
   @GetMapping("/all")
    public List<Quiz> getAll(){
        return iQuizService.getALl();
    }
    @PostMapping("/create/{quizName}/{timeLimit}/{subjectName}/{teacherId}")
    public ResponseEntity<Quiz> createQuiz(
            @PathVariable String quizName,
            @PathVariable Integer timeLimit,
            @PathVariable String subjectName,
            @PathVariable Integer teacherId) {

        try {
            Quiz createdQuiz = quizService.createQuiz(quizName, timeLimit, subjectName, teacherId);
            return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{quizName}/{newQuizName}/{newTimeLimit}/{newIsCompleted}")

    public ResponseEntity<Quiz> updateQuiz(
            @PathVariable String quizName,
            @PathVariable String newQuizName,
            @PathVariable Integer newTimeLimit,
            @PathVariable Boolean newIsCompleted
    ) {
        Optional<Quiz> updatedQuiz = quizService.updateQuizByQuizName(quizName, newQuizName, newTimeLimit, newIsCompleted);

        return updatedQuiz.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

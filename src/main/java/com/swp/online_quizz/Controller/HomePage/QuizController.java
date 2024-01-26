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
    @PostMapping("/create")
    public Quiz createQuiz(
            @RequestParam String quizName,
            @RequestParam Integer timeLimit,
            @RequestParam Subject subject,
            @RequestParam Integer teacherId,
            @RequestBody List<Question> questions) {

        return quizService.createQuiz(quizName, timeLimit, subject, teacherId, questions);
    }
}

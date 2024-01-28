package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.IAnswerService;
import com.swp.online_quizz.Service.IQuestionService;
import com.swp.online_quizz.Service.IQuizService;
import com.swp.online_quizz.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
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
    @Autowired
    private IQuestionService iQuestionService;
    @Autowired
    private IAnswerService iAnswerService;
   @GetMapping("/all")
    public List<Quiz> getAll(){
        return iQuizService.getALl();
    }

    @GetMapping("")
    public String showQuiz() {
        return "showQuiz";
    }



    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@ModelAttribute("user") Quiz quiz,
            @RequestParam(name="quizName") String quizName,
            @RequestParam(name="timeLimit") Integer timeLimit,
            @RequestParam(name="subjectName") String subjectName,
            @RequestParam(name="teacherId") Integer teacherId) {

        try {
            Quiz createdQuiz = quizService.createQuiz(quizName, timeLimit, subjectName, teacherId);
            return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/createAll")
    public ResponseEntity<String> createQuizQuestionAnswer(
            @ModelAttribute("user") Quiz quiz,
            @RequestParam(name = "quizName") String quizName,
            @RequestParam(name = "timeLimit") Integer timeLimit,
            @RequestParam(name = "subjectName") String subjectName,
            @RequestParam(name = "teacherId") Integer teacherId,
            @RequestParam(name = "questions[0].questionContent") String questionContent,
            @RequestParam(name = "questions[0].questionType") String questionType,
            @RequestParam(name = "imageURL", required = false) String imageURL,
            @RequestParam(name = "videoURL", required = false) String videoURL,
            @RequestParam(name = "questions[0].answers[0].answerContent") String answerContent,
            @RequestParam(name = "questions[0].answers[0].correct") boolean isCorrect
    ) {
        try {
            // Create Quiz
            Quiz createdQuiz = quizService.createQuiz(quizName, timeLimit, subjectName, teacherId);

            // Create Question
            Question createdQuestion = iQuestionService.createQuestion(createdQuiz.getQuizId(), questionContent, questionType, imageURL, videoURL);

            // Create Answer
            iAnswerService.createAnswer(answerContent, createdQuestion.getQuestionId(), isCorrect);

            return new ResponseEntity<>("Quiz, Question, and Answer created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create Quiz, Question, and Answer. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/showCreateQuizPage")
    public String showCreateQuizPage() {
        return "createQuiz";
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

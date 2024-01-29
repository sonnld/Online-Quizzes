package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Service.IAnswerService;
import com.swp.online_quizz.Service.IQuestionService;
import com.swp.online_quizz.Service.IQuizService;
import com.swp.online_quizz.Service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private QuizRepository quizRepository;
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

    @GetMapping("/list")
    public String showQuizList(Model model) {
        List<Quiz> quizList = iQuizService.getALl(); // Thay thế bằng phương thức lấy danh sách quiz từ Service
        model.addAttribute("quizList", quizList);
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
    @PutMapping("/update/{quizId}/{newQuizName}/{newTimeLimit}/{newIsCompleted}")

    public ResponseEntity<Quiz> updateQuiz(
            @PathVariable Integer quizId,
            @PathVariable String newQuizName,
            @PathVariable Integer newTimeLimit,
            @PathVariable Boolean newIsCompleted
    ) {
        Optional<Quiz> updatedQuiz = quizService.updateQuizByQuizId(quizId, newQuizName, newTimeLimit, newIsCompleted);

        return updatedQuiz.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PutMapping("/updateAll/{quizId}")
//    public ResponseEntity<String> updateQuizQuestionAnswer(
//            @ModelAttribute("user") Quiz quiz,
//            @PathVariable Integer quizId,
//            @RequestParam(name = "quizName") String quizName,
//            @RequestParam(name = "timeLimit") Integer timeLimit,
//            @RequestParam(name = "subjectName") String subjectName,
//            @RequestParam(name = "isCompleted") boolean isCompleted,
//            @RequestParam(name = "questions[0].questionId") Integer questionId,
//            @RequestParam(name = "questions[${questionIndex.index}].questionContent") String questionContent,
//            @RequestParam(name = "questions[${questionIndex.index}].questionType") String questionType,
//            @RequestParam(name = "imageURL", required = false) String imageURL,
//            @RequestParam(name = "videoURL", required = false) String videoURL,
//            @RequestParam(name = "questions[0].answers[0].answerId") Integer answerId,
//            @RequestParam(name = "questions[${questionIndex.index}].answers[${answerIndex.index}].answerContent") String answerContent,
//            @RequestParam(name = "questions[${questionIndex.index}].answers[${answerIndex.index}].correct") boolean isCorrect
//    ) {
//        try {
//            // Update Quiz
//            Optional<Quiz> updatedQuizOpt = quizService.updateQuizByQuizId(quizId, quizName, timeLimit, isCompleted);
//
//            if (updatedQuizOpt.isPresent()) {
//                // Update Question
//                iQuestionService.updateQuestion(questionId, questionContent, questionType, imageURL, videoURL);
//
//                // Update Answer
//                iAnswerService.updateAnswer(answerId, answerContent, isCorrect);
//
//                return new ResponseEntity<>("Quiz, Question, and Answer updated successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>("Failed to update Quiz, Question, and Answer. " + e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to update Quiz, Question, and Answer. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@PostMapping("/updateAll/{quizId}")
public ResponseEntity<String> updateQuizAll(//@ModelAttribute("user") Quiz quiz,
                                         @PathVariable Integer quizId,
                                         @RequestParam(name="quizName") String newQuizName,
                                         @RequestParam(name="timeLimit") Integer newTimeLimit,
                                         @RequestParam(name="newIsCompleted") Boolean newIsCompleted,
                                         @RequestParam(name="questionId") List<Integer> questionId,
                                         @RequestParam(name="newQuestionContent") List<String> newQuestionContent,
                                         @RequestParam(name="newQuestionType") List<String> newQuestionType,
                                         @RequestParam(name="newImageURL", required = false) List<String> newImageURL,
                                         @RequestParam(name="newVideoURL", required = false) List<String> newVideoURL,
                                         @RequestParam(name="answerId") List<List<Integer>> answerId,
                                         @RequestParam(name="newAnswerContent") List<List<String>> newAnswerContent,
                                         @RequestParam(name="newIsCorrect") List<List<Boolean>> newIsCorrect) {
    try {
         quizService.updateAll(quizId, newQuizName, newTimeLimit, newIsCompleted,
                questionId, newQuestionContent, newQuestionType, newImageURL, newVideoURL,
                answerId, newAnswerContent, newIsCorrect);

        return new ResponseEntity<>("Quiz updated successfully", HttpStatus.OK);
    } catch (EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>("An error occurred while updating the quiz", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @GetMapping("/showUpdateQuizPage/{quizId}")
    public String getUpdateQuizForm(@PathVariable Integer quizId, Model model) {
        // Retrieve the quiz and its details from the service
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            return "showQuiz";
        }
        model.addAttribute("quiz", quiz);
        return "updateQuiz";
    }
    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<String> deleteQuizzes(@PathVariable Integer quizId) {
        String message;
        try {
            iQuizService.deleteQuizById(quizId);
            message = "Quiz deleted successfully!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Error deleting quiz: " + e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

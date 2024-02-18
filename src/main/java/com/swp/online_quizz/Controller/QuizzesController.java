package com.swp.online_quizz.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.ExcelUploadService;
import com.swp.online_quizz.Service.IAnswerService;
import com.swp.online_quizz.Service.IClassQuizzService;
import com.swp.online_quizz.Service.IFeedbackService;
import com.swp.online_quizz.Service.IQuestionAttemptsService;
import com.swp.online_quizz.Service.IQuestionsService;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizProgressService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.ISubjectService;
import com.swp.online_quizz.Service.IUsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/quizzes")
public class QuizzesController {
    @Autowired
    private ExcelUploadService excelUploadService;
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IQuizAttemptsService iQuizAttemptsService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private IQuizzesService iQuizService;
    @Autowired
    private IQuestionsService iQuestionsService;
    @Autowired
    private IQuizProgressService iQuizProgressService;
    @Autowired
    private IQuestionAttemptsService iQuestionAttemptsService;
    @Autowired
    private IAnswerService iAnswerService;
    @Autowired
    private IFeedbackService iFeedbackService;
    @Autowired
    private IClassQuizzService iClassQuizzService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ISubjectService iSubjectService;

    @GetMapping("/all")
    public List<Quiz> getAll() {
        return iQuizzesService.getAll();
    }

    @GetMapping("/list")
    public String showQuizList(Model model) {
        List<Quiz> quizList = iQuizzesService.getAll(); // Thay thế bằng phương thức lấy danh sách quiz từ Service
        model.addAttribute("quizList", quizList);
        return "showQuiz";
    }

    @Transactional
    @PostMapping("/createAll")
    public String createQuizWithQuestionsAndAnswers(@ModelAttribute("quiz") Quiz quiz) {

        String subjectName = quiz.getSubjectName();

        Subject subject = new Subject();
        subject.setSubjectName(subjectName);

        quiz.setSubject(subject);
        if (quiz.getTeacher() == null) {

            User defaultTeacher = iUsersService.getUsersByID(1);
            quiz.setTeacher(defaultTeacher);
        }
        boolean quizCreated = iQuizService.createQuiz1(quiz);

        if (quizCreated) {

            for (Question question : quiz.getListQuestions()) {

                question.setQuiz(quiz);

                iQuestionsService.createQuestion1(question);

                for (Answer answer : question.getListAnswer()) {

                    answer.setQuestion(question);
                    iAnswerService.createAnswer1(answer, question.getQuestionId());
                }
            }
            return "redirect:/quizzes/list";
        } else {
            return "redirect:/quizzes/createAll";
        }
    }

    @GetMapping("/showCreateQuizPage")
    public String showCreateQuizPage(Model model) {
        Quiz quiz = iQuizzesService.getEmptyQuiz();
        model.addAttribute("quiz", new Quiz());
        List<String> questionContents = quiz.getListQuestions().stream()
                .map(Question::getQuestionContent)
                .collect(Collectors.toList());

        model.addAttribute("questionContents", questionContents);

        return "createQuiz";
    }

    @PostMapping("/updateAll/{quizId}")
    public String updateQuizAndQuestions(@PathVariable Integer quizId, @ModelAttribute("quiz") Quiz updatedQuiz) {
        iQuizzesService.updateQuizByQuizId1(quizId, updatedQuiz);

        for (Question updatedQuestion : updatedQuiz.getListQuestions()) {
            Question existingQuestion = iQuestionsService.findQuestionById(updatedQuestion.getQuestionId());

            if (existingQuestion != null) {
                iQuestionsService.updateQuestion1(existingQuestion.getQuestionId(), updatedQuestion);
            } else {
                updatedQuestion.setQuiz(updatedQuiz);
                iQuestionsService.createQuestion1(updatedQuestion);
            }

            for (Answer updatedAnswer : updatedQuestion.getListAnswer()) {
                Answer existingAnswer = iAnswerService.getAnswerById(updatedAnswer.getAnswerId());

                if (existingAnswer != null) {
                    iAnswerService.updateAnswer1(existingAnswer.getAnswerId(), updatedAnswer);
                } else {
                    updatedAnswer.setQuestion(updatedQuestion);
                    iAnswerService.createAnswer1(updatedAnswer, updatedQuestion.getQuestionId());
                }
            }
        }

        return "redirect:/quizzes/list";
    }

    @GetMapping("/showUpdateQuizPage/{quizId}")
    public String getUpdateQuizForm(@PathVariable Integer quizId, Model model) {

        Quiz quiz = iQuizService.findQuizById(quizId);
        if (quiz == null) {
            return "showQuiz";
        }
        model.addAttribute("quiz", quiz);

        return "updateQuiz";
    }

    @GetMapping("/delete/{quizId}")
    public String deleteQuiz(@PathVariable Integer quizId) {
        try {
            iQuizProgressService.deleteQuizProcessByQuizId(quizId);
            iQuestionAttemptsService.deleteQuestionAttemptsByQuizId(quizId);
            List<QuizAttempt> attempts = iQuizAttemptsService.getQuizAttemptsByQuizId(quizId);
            for (QuizAttempt attempt : attempts) {
                iFeedbackService.deleteFeedbackByAttemptId(attempt.getAttemptId());
            }
            iQuizAttemptsService.deleteQuizAttemptsByQuizId(quizId);
            iClassQuizzService.deleteClassQuizzByQuizId(quizId);
            iQuestionsService.deleteQuestionsByQuizId(quizId);
            List<Question> questions = iQuestionsService.getQuestionsByQuizId(quizId);
            for (Question question : questions) {
                iAnswerService.deleteAnswersByQuestionId(question.getQuestionId());
            }

            iQuizzesService.deleteQuizById(quizId);
            return "redirect:/quizzes/list";
        } catch (Exception e) {
            return "redirect:/quizzes/list";
        }
    }

    @GetMapping("/{quizID}")
    public String quizInfo(@PathVariable Integer quizID, HttpSession session, Model model, Authentication auth,
            HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            // Nếu không có user thì làm gì đấy
            return "redirect:/login";
        }
        // nếu có thì lấy ra user
        User user1 = userOptional.get();

        Quiz quiz = iQuizzesService.getOneQuizz(quizID);
        List<QuizAttempt> listAttempts = iQuizAttemptsService.getAttemptByUserIdAndQuizzId(quiz, user1);
        Integer highestMark = 0;
        for (QuizAttempt quizAttempt : listAttempts) {
            if (quizAttempt.getMarks() > highestMark) {
                highestMark = quizAttempt.getMarks();
            }
        }
        model.addAttribute("listAttempts", listAttempts);
        model.addAttribute("quiz", quiz);
        model.addAttribute("highestMark", highestMark);
        return "quizzInfo";

    }

    @GetMapping("/Importxlsx")
    public String test() {
        return "Importxlsx";
    }

    @PostMapping("/uploadquizdata")
    public String uploadQuizData(@RequestParam("file") MultipartFile file, HttpSession session, Model model,
            Authentication auth, HttpServletRequest request) throws IOException {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            // Nếu không có user thì làm gì đấy
            return "login";
        }
        // nếu có thì lấy ra user
        User user1 = userOptional.get();
        Quiz quiz = excelUploadService.createQuizFromExcel(file, user1);
        return "showQuiz";
    }

    @GetMapping("/downloadsample")
    public ResponseEntity<?> downloadSample() throws IOException {
        String fileName = "ExampleFormQuiz.xlsx";
        InputStream is = this.getClass().getResourceAsStream("/ExampleFormQuiz.xlsx");
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(IOUtils.toByteArray(is));
        } catch (IntegrationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

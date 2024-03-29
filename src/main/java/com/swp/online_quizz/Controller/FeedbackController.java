package com.swp.online_quizz.Controller;

import java.util.Optional;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swp.online_quizz.Entity.Feedback;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.FeedbackRepository;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IFeedbackService;
import com.swp.online_quizz.Service.IMessagesService;
import com.swp.online_quizz.Service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/feedback")
public class FeedbackController {
    private final IFeedbackService iFeedbackService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private QuizAttemptsRepository quizAttemptsRepository;
    @Autowired
    private IMessagesService iMessagesService;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackController(IFeedbackService feedbackService) {
        this.iFeedbackService = feedbackService;
    }

    @GetMapping("/attemptQuiz/{attemptID}/feedbacks")
    public String viewFeedbacks(@PathVariable Integer quizId, @PathVariable Integer attemptID, Model model) {

        List<Feedback> feedbacks = feedbackRepository.findByAttemptAttemptId(attemptID);

        model.addAttribute("feedbacks", feedbacks);

        return "ReviewMark";
    }

    @PostMapping("/createFeedback/{attemptID}")
    public String createFeedback(@ModelAttribute("feedback") Feedback feedback,
            @PathVariable Integer attemptID,
            HttpServletRequest request) {

        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOptional.get();
        QuizAttempt existingQuizAttempt = quizAttemptsRepository.findByAttemptId(attemptID);
        if (existingQuizAttempt == null) {

            return "redirect:/login";
        }

        feedback.setUser(user);
        feedback.setAttempt(existingQuizAttempt);

        feedback = iFeedbackService.createFeedback(feedback);
        iMessagesService.createNotificationNewFeedback(feedback);
        return "redirect:/class/mark/" + existingQuizAttempt.getQuiz().getQuizId() + "/attempt/" + attemptID;
    }

    @PostMapping("/updateFeedback/{feedbackID}")
    public String updateFeedback(@PathVariable Integer feedbackID,
            @ModelAttribute("feedback") Feedback feedback) {
        Feedback fb = feedbackRepository.getReferenceById(feedbackID);
        fb.setComment(feedback.getComment());
        feedbackRepository.save(fb);
        return "redirect:/class/mark/" + fb.getAttempt().getQuiz().getQuizId() + "/attempt/"
                + fb.getAttempt().getAttemptId();
    }

    @GetMapping("/deleteFeedback/{attemptID}/{feedbackID}")
    public String deleteFeedback(@PathVariable Integer feedbackID,
            @PathVariable Integer attemptID,
            @ModelAttribute("feedback") Feedback feedback) {
        QuizAttempt existingQuizAttempt = quizAttemptsRepository.findByAttemptId(attemptID);
        iFeedbackService.deleteFeedbackByFeedbackId(feedbackID);
        return "redirect:/class/mark/" + existingQuizAttempt.getQuiz().getQuizId() + "/attempt/" + attemptID;
    }

}

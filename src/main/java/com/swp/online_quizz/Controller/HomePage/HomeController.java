package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.QuizzesService;
import com.swp.online_quizz.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HomeController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private QuizzesService quizzesService;
    @RequestMapping("")
    public String Home(Model model){
        List<Subject> listSubject = subjectService.getAll();
        List<Quiz> listQuiz = quizzesService.getAll();
        model.addAttribute("listSubject",listSubject);
        model.addAttribute("listQuizzes",listQuiz);
        return "HomePage";
    }
    @GetMapping("/{subjectId}")
    public List<Quiz> getQuizzesBySubjectId(@PathVariable Integer subjectId) {
        return subjectService.getQuizzesBySubjectId(subjectId);
    }
    @GetMapping("/search")
    public List<Quiz> searchQuizzes(@RequestParam String keyword) {
        return quizzesService.searchQuizzes(keyword);
    }
}

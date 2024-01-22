package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Service.IQuizzesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizzesController {
    @Autowired
    private IQuizzesService iQuizzesService;
    @GetMapping("/list")
    public List<Quizzes> getAllQuizz() {

        return iQuizzesService.getAllQuizzes();
    }
    @GetMapping("/list/{quizId}")
    public Quizzes getQuizById(@PathVariable Integer quizId) {
        return iQuizzesService.getQuizById(quizId);
    }

    @PostMapping("/create")
    public boolean createQuiz(@RequestBody Quizzes quiz) {
        return iQuizzesService.createQuiz(quiz);
    }

    @PutMapping("/update/{quizId}")
    public Quizzes updateQuiz(@PathVariable Integer quizId, @RequestBody Quizzes updatedQuiz) {
        return iQuizzesService.updateQuiz(quizId, updatedQuiz);
    }

    @DeleteMapping("/delete/{quizId}")
    public boolean deleteQuiz(@PathVariable Integer quizId) {

        return iQuizzesService.deleteQuiz(quizId);
    }
}
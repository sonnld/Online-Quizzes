package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.IQuizService;
import com.swp.online_quizz.Service.ISubjectService;
import com.swp.online_quizz.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;
    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @Autowired
    private ISubjectService iSubjectService;
    @GetMapping("/all")
    public List<Subject> getAll(){
        return iSubjectService.getALl();
    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Subject createSubject(@RequestBody Subject subject) {
        try {
            return subjectService.createOrUpdateSubject(subject);
        } catch (Exception e) {

            throw new RuntimeException( e);
        }
    }

    @PostMapping("/cre")
        public Subject creSubject(@RequestBody Subject subject){
            return subjectService.createSubject(subject);

    }

}

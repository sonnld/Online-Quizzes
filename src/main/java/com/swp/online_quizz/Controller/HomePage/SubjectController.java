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
import java.util.Optional;

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
    @GetMapping("/create/{subjectName}")

    public Subject createSubject(@PathVariable String subjectName) {
        try {
            return subjectService.createOrUpdateSubject(subjectName);
        } catch (Exception e) {

            throw new RuntimeException( e);
        }
    }
    @PutMapping("/update/{subjectName}/{newSubjectName}/{newDescription}")
    public ResponseEntity<Subject> updateSubjectByName(
            @PathVariable String subjectName,
            @PathVariable String newSubjectName,
            @PathVariable String newDescription
            ) {

        Optional<Subject> updatedSubject = subjectService.updateSubjectBySubjectName(subjectName,
                                                                                        newSubjectName, newDescription);

        if (updatedSubject.isPresent()) {
            return new ResponseEntity<>(updatedSubject.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/cre")
//        public Subject creSubject(@RequestBody Subject subject){
//            return subjectService.createSubject(subject);
//
//    }

}

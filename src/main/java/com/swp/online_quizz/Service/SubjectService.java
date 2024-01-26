package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements ISubjectService{

   @Autowired
   private SubjectRepository subjectRepository;
    @Override
    public List<Subject> getALl() {
        return subjectRepository.findAll();
    }
   @Override
    public Subject getSubjectByName(String subjectName) {
        return subjectRepository.findBySubjectName(subjectName)
                .orElse(null);
    }
    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
    @Override
    public Subject createOrUpdateSubject(Subject subject) {
        String subjectName = subject.getSubjectName();
        Subject existingSubject = getSubjectByName(subjectName);

        if (existingSubject != null) {
            return existingSubject;
        } else {
            return createSubject(subject);
        }
    }
}

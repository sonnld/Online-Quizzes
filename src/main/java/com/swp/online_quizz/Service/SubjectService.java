package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Subject createOrUpdateSubject(String subjectName) {

        Subject existingSubject = getSubjectByName(subjectName);

        if (existingSubject != null) {
            return existingSubject;
        } else {
            Subject subject = new Subject(subjectName,"");
            return createSubject(subject);
        }
    }

    @Override
    @Transactional
    public Optional<Subject> updateSubjectBySubjectName(String subjectName, String newSubjectName, String newDescription) {
        subjectRepository.updateSubjectBySubjectName(subjectName, newSubjectName, newDescription);


        return subjectRepository.findBySubjectName(newSubjectName);
    }
}
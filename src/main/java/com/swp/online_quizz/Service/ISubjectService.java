package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ISubjectService {
    List<Subject> getALl();

    Subject getSubjectByName(String subjectName);

    public Subject createSubject(Subject subject);


    Subject createOrUpdateSubject(String subjectName);

    @Transactional
    Optional<Subject> updateSubjectBySubjectName(String subjectName, String newSubjectName, String newDescription);
}

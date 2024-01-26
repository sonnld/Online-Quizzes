package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface ISubjectService {
    List<Subject> getALl();

    Subject getSubjectByName(String subjectName);

    public Subject createSubject(Subject subject);


    Subject createOrUpdateSubject(Subject subject);
}

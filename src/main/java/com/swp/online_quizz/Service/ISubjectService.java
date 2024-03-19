package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.swp.online_quizz.Entity.Subject;

public interface ISubjectService {
    public List<Subject> getAll();

    public boolean create(Subject subjects);



    Subject getSubjectByName(String subjectName);

    Subject createSubject(Subject subject);

    Subject createOrUpdateSubject(String subjectName);

    @Transactional
    Optional<Subject> updateSubjectBySubjectName(String subjectName, String newSubjectName, String newDescription);


    public Subject find(Integer subjectID);

    public Boolean update(Subject subjects);

    public Boolean delete(Integer subjectID);


    List<Subject> getSubjectsByClasses(List<Classes> classes);
}

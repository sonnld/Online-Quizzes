package com.swp.online_quizz.Entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quizzes {
    @Id
    @Column(name = "QuizID")
    private Integer quizId;

    @Column(name = "TeacherID")
    private Integer teacherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID")
    @JsonBackReference
    private Subjects subject;

    @Column(name = "QuizName")
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "isCompleted")
    private Boolean isCompleted;

    @OneToMany(mappedBy = "quiz")
    @JsonManagedReference
    private Set<Questions> listQuestions;

}

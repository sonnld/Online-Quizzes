package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Quizzes")
public class Quiz {
    @Id
    @Column(name = "QuizID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeacherID")
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID")
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subject subject;

    @Column(name = "SubjectName", length = 100)
    private String subjectName; // Thêm cột subjectName

    @Column(name = "QuizName", length = 100)
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @OneToMany(mappedBy = "quiz")
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();
    public Quiz(User teacher, Subject subjectName, String quizName, Integer timeLimit, Boolean isCompleted) {
        this.teacher = teacher;
        this.subject = subjectName;
        this.quizName = quizName;
        this.timeLimit = timeLimit;
        this.isCompleted = isCompleted;
    }
}
package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnswerID", nullable = false)
    @JsonIgnore
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID")
    @JsonBackReference
    private Question question;

    @Lob
    @Column(name = "AnswerContent")
    private String answerContent;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;



    public Answer(String answerContent, Boolean isCorrect) {
        this.answerContent = answerContent;
        this.isCorrect = isCorrect;
    }

    public Answer() {

    }
}
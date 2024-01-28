package com.swp.online_quizz.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QuestionAttempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAttempts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionAttemptID")
    private Integer questionAttemptID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    @JsonBackReference
    private QuizAttempts attempt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QuestionID")
    private Questions question;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "QuestionOrder")
    private Integer questionOrder;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

    public QuestionAttempts(QuizAttempts attempt, Questions question, String answer, Boolean isAnswered, Integer questionOrder, Boolean isCorrect) {
        this.attempt = attempt;
        this.question = question;
        this.answer = answer;
        this.isAnswered = isAnswered;
        this.questionOrder = questionOrder;
        this.isCorrect = isCorrect;
    }
}

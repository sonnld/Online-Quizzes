package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @Column(name = "QuestionID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    private Quiz quizID;

    @Lob
    @Column(name = "QuestionContent")
    private String questionContent;

    @Column(name = "QuestionType", length = 20)
    private String questionType;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "VideoURL")
    private String videoURL;

    @ManyToOne
    @JoinColumn(name = "AnotherQuizID")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public Question(Quiz quizID, String questionContent, String questionType, List<Answer> answers) {
        this.quizID = quizID;
        this.questionContent = questionContent;
        this.questionType = questionType;
        this.answers = answers;
        initializeAnswers();
    }

    public Question() {

    }

    private void initializeAnswers() {
        if (this.answers != null) {
            for (Answer answer : this.answers) {
                answer.setQuestion(this);
            }
        }
    }
}

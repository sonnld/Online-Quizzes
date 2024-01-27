package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    @JsonBackReference
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



    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    private List<Answer> answers = new ArrayList<>();



    public Question( String questionContent, String questionType, String imageURL, String videoURL) {

        this.questionContent = questionContent;
        this.questionType = questionType;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
    }

    public Question() {

    }




}

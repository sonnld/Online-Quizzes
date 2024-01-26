package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Subjects")
public class Subject {
    @Id
    @Column(name = "SubjectID", nullable = false)
   // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    @Column(name = "SubjectName", nullable = false, length = 100)
    private String subjectName;

    @Lob
    @Column(name = "Description")
    private String description;

    public Subject(String subjectName, String description) {
        this.subjectName = subjectName;
        this.description = description;
    }


}
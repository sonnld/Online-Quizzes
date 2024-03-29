package com.swp.online_quizz.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[Users]")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Username", nullable = false, length = 50)
    private String username;

    @Column(name = "PasswordHash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Role", nullable = false, length = 20)
    private String role;

    @Column(name = "JoinDate")
    private LocalDateTime joinDate;

    @Column(name = "FirstName", length = 50)
    private String firstName;

    @Column(name = "LastName", length = 50)
    private String lastName;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "Address")
    private String address;

    @Column(name = "Gender", length = 10)
    private String gender;

    @Column(name = "ProfilePictureURL")
    private String profilePictureURL;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "Otp", length = 6)
    private String otp;

    @Column(name = "OtpGeneratedTime")
    private LocalDateTime otpGeneratedTime;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<QuizAttempt> listQuizAttempts;

    @OneToMany(mappedBy = "studentID")
    @JsonManagedReference
    private Set<ClassEnrollment> listEnrollment;
}

package com.example.notice.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String lectureName;
    private Status status;
    @Column(nullable = false)
    private Integer maximumNumber;
    @Column(nullable = false)
    private Integer score;
    private String lectureComment;
    @Column(nullable = false)
    private LocalDateTime lectureDate;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    private Long roomId;
    private Long majorId;
    private String professorId;

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", lectureName='" + lectureName + '\'' +
                ", status=" + status +
                ", maximumNumber=" + maximumNumber +
                ", score=" + score +
                ", lectureComment='" + lectureComment + '\'' +
                ", lectureDate=" + lectureDate +
                ", semester=" + semester +
                ", room=" + roomId +
                ", major=" + majorId +
                ", professor=" + professorId +
                '}';
    }
}

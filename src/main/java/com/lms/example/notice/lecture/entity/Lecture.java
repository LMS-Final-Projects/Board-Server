package com.lms.example.notice.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture {

    @Id
    private Integer lectureId;
    @Column(nullable = false)
    private String memberId;
    @Column(nullable = false)
    private String lectureName;
    @Column(nullable = false)
    private String professorName;
    @Column(nullable = false)
    private Integer score;
    @Column(nullable = false)
    private Integer startTime;
    @ElementCollection
    private List<Integer> classTimes; // 해당 교시
    @Column
    private DayOfWeek dayOfWeek;
    @Column
    private Integer maximumNumber;
    @Column
    private Integer year;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    @Column
    private String contents;

}

package com.lms.example.notice.board.dto.response;

import com.lms.example.notice.board.entity.ClassBoards;
import com.lms.example.notice.board.entity.ClassFiles;
import com.lms.example.notice.lecture.entity.Lecture;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ClassBoardRes {

    private UUID id;
    private String memberId;
    private String professorName;
    private String memberName;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<ClassFiles> classFiles;
    private Integer lectureId;
    private String contents;
    private Integer startTime;
    private DayOfWeek dayOfWeek;

    public ClassBoardRes(ClassBoards classBoards) {
        this.id = classBoards.getClassId();
        this.professorName = classBoards.getProfessorName();
        this.memberId = classBoards.getMember().getId();
        this.memberName = classBoards.getMember().getName();
        this.contents = classBoards.getContents();
        this.title = classBoards.getTitle();
        this.createAt = classBoards.getCreateAt();
        this.updateAt = classBoards.getUpdateAt();
        this.classFiles = classBoards.getClassFiles();
        this.lectureId = classBoards.getLectureId();
        this.startTime = classBoards.getStartTime();
        this.dayOfWeek = classBoards.getDayOfWeek();
    }
}

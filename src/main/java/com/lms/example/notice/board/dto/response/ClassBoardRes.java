package com.lms.example.notice.board.dto.response;

import com.lms.example.notice.board.entity.ClassBoards;
import com.lms.example.notice.board.entity.ClassFiles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ClassBoardRes {

    private UUID id;
    private String memberId;
    private String email;
    private String memberName;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<ClassFiles> classFiles;
    private Long lectureId;

    public ClassBoardRes(ClassBoards classBoards) {
        this.id = classBoards.getClassId();
        this.memberId = classBoards.getMember().getId();
        this.memberName = classBoards.getMember().getName();
        this.email = classBoards.getEmail();
        this.title = classBoards.getTitle();
        this.createAt = classBoards.getCreateAt();
        this.updateAt = classBoards.getUpdateAt();
        this.classFiles = classBoards.getClassFiles();
        this.lectureId = classBoards.getLectureId();
    }
}

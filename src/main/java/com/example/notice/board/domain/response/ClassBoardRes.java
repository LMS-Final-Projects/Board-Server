package com.example.notice.board.domain.response;

import com.example.notice.board.domain.entity.ClassBoards;
import com.example.notice.board.domain.entity.ClassFiles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ClassBoardRes {

    private Long id;
    private String userId;
    private String email;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<ClassFiles> classFiles;
    private Long lectureId;

    public ClassBoardRes(ClassBoards classBoards) {
        this.id = classBoards.getId();
        this.userId = classBoards.getUserId();
        this.email = classBoards.getEmail();
        this.title = classBoards.getTitle();
        this.createAt = classBoards.getCreateAt();
        this.updateAt = classBoards.getUpdateAt();
        this.classFiles = classBoards.getClassFiles();
        this.lectureId = classBoards.getLectureId();
    }
}

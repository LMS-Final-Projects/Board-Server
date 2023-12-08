package com.example.notice.admin.dto;

import com.example.notice.board.domain.entity.ClassBoards;
import com.example.notice.board.domain.entity.ClassFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CLassDto {

    private String userId;
    private Long classId;
    private String email;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateTime;
    private List<ClassFiles> classFiles;

    public CLassDto(String userId, Long classId, String email, String title, LocalDateTime createAt, LocalDateTime updateTime, List<ClassFiles> classFiles) {
        this.userId = userId;
        this.classId = classId;
        this.email = email;
        this.title = title;
        this.createAt = createAt;
        this.updateTime = updateTime;
        this.classFiles = classFiles;
    }
    public CLassDto(ClassBoards classBoards) {
        this.userId= classBoards.getUserId();
        this.classId = classBoards.getId();
        this.email = classBoards.getEmail();
        this.title = classBoards.getTitle();
        this.createAt = classBoards.getCreateAt();
        this.updateTime = classBoards.getUpdateAt();
        this.classFiles = classBoards.getClassFiles();
    }

    public ClassBoards toEntity(){
        return ClassBoards
                .builder()
                .userId(userId)
                .id(classId)
                .email(email)
                .title(title)
                .createAt(createAt)
                .updateAt(updateTime)
                .classFiles(classFiles)
                .build();
    }


    public void changeUpdateTime(){
        this.updateTime = LocalDateTime.now();
    }


}

package com.example.notice.admin.dto;

import com.example.notice.board.domain.entity.Notice;
import com.example.notice.board.domain.entity.NoticeFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class NoticeDto {

    private String userId;
    private Long noticeId;
    private String email;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<NoticeFiles> noticeFiles;

    public NoticeDto(String userId, Long noticeId, String email, String title, LocalDateTime createAt, LocalDateTime updateAt, List<NoticeFiles> noticeFiles) {
        this.userId = userId;
        this.noticeId = noticeId;
        this.email = email;
        this.title = title;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.noticeFiles = noticeFiles;
    }

    public NoticeDto(Notice notice) {
        this.userId = notice.getUserId();
        this.noticeId = notice.getId();
        this.email = notice.getEmail();
        this.title = notice.getTitle();
        this.createAt = notice.getCreateAt();
        this.updateAt = notice.getUpdateAt();
        this.noticeFiles = notice.getNoticeFiles();
    }

    public Notice toEntity(){
        return Notice
                .builder()
                .id(noticeId)
                .userId(userId)
                .email(email)
                .title(title)
                .createAt(createAt)
                .updateAt(updateAt)
                .noticeFiles(noticeFiles)
                .build();
    }


    public void changeUpdateTime(){
        this.updateAt = LocalDateTime.now();
    }

}

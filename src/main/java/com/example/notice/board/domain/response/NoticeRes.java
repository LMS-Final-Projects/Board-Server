package com.example.notice.board.domain.response;
import com.example.notice.board.domain.entity.Notice;
import com.example.notice.board.domain.entity.NoticeFiles;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class NoticeRes {

    private UUID id;
    private UUID memberId;
    private String email;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<NoticeFiles> noticeFiles;

    public NoticeRes(Notice noticeDto) {
        this.id = noticeDto.getNoticeId();
        this.memberId = noticeDto.getMemberId();
        this.email = noticeDto.getEmail();
        this.title = noticeDto.getTitle();
        this.content =noticeDto.getContent();
        this.createAt = noticeDto.getCreateAt();
        this.updateAt = noticeDto.getUpdateAt();
        this.noticeFiles = noticeDto.getNoticeFiles();
    }
}

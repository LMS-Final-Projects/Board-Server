package com.lms.example.notice.board.dto.response;
import com.lms.example.notice.board.entity.Notice;
import com.lms.example.notice.board.entity.NoticeFiles;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class NoticeRes {
    private UUID id;
    private String email;
    private String memberName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<NoticeFiles> noticeFiles;

    public NoticeRes(Notice Notice) {
        this.id = Notice.getNoticeId();
        this.email = Notice.getEmail();
        this.memberName = Notice.getMember().getName();
        this.title = Notice.getTitle();
        this.content =Notice.getContent();
        this.createAt = Notice.getCreateAt();
        this.updateAt = Notice.getUpdateAt();
        this.noticeFiles = Notice.getNoticeFiles();
    }
}

package com.example.notice.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeReplyCommentDto {
    private Long id;
    private String userId;
    private String comments;
    private LocalDateTime createAt;
    private String userEmail;
    private Long adminBoardId;
    private Long noticeCommentId;

    public NoticeReplyCommentDto(NoticeReplyComment noticeReplyComment) {
        this.id = noticeReplyComment.getId();
        this.userId = noticeReplyComment.getUserId();
        this.comments = noticeReplyComment.getComments();
        this.createAt = noticeReplyComment.getCreateAt();
        this.userEmail =noticeReplyComment.getUserEmail();
        this.adminBoardId = noticeReplyComment.getAdminBoardId();
        this.noticeCommentId = noticeReplyComment.getNoticeCommentId();
    }

    public NoticeReplyCommentDto(Long id, String userId, String comments, LocalDateTime createAt, String userEmail, Long adminBoardId, Long noticeCommentId) {
        this.id = id;
        this.userId = userId;
        this.comments = comments;
        this.createAt = createAt;
        this.userEmail = userEmail;
        this.adminBoardId = adminBoardId;
        this.noticeCommentId = noticeCommentId;
    }


    public NoticeReplyComment toEntity(){
        return NoticeReplyComment
                .builder()
                .id(id)
                .userId(userId)
                .comments(comments)
                .createAt(createAt)
                .userEmail(userEmail)
                .adminBoardId(adminBoardId)
                .noticeCommentId(noticeCommentId)
                .build();

    }
}

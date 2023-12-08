package com.example.notice.admin.dto;

import java.time.LocalDateTime;

public class NoticeCommentDto {
    private Long id;
    private String userEmail;
    private String comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long adminBoardId;

    public NoticeCommentDto(NoticeComment noticeComment) {
        this.id = noticeComment.getId();
        this.userEmail = noticeComment.getUserEmail();
        this.comments = noticeComment.getComments();
        this.createAt = noticeComment.getCreateAt();
        this.userEmail = noticeComment.getUserEmail();
        this.adminBoardId = noticeComment.getAdminBoardId();
    }

    public NoticeCommentDto(Long id, String userEmail, String comments, LocalDateTime createAt, Long adminBoardId) {
        this.id = id;
        this.userEmail = userEmail;
        this.comments = comments;
        this.createAt = createAt;

        this.adminBoardId = adminBoardId;
    }

    public NoticeComment toEntity(){
        return NoticeComment
                .builder()
                .id(id)
                .userEmail(userEmail)
                .comments(comments)
                .createAt(createAt)
                .adminBoardId(adminBoardId)
                .build();
    }
}

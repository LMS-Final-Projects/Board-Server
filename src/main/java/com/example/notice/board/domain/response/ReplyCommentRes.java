package com.example.notice.board.domain.response;

import com.example.notice.board.domain.entity.ReplyComments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReplyCommentRes {
    private Long id;
    private UUID memberId;
    private String comments;
    private LocalDateTime createAt;
    private String email;
    private LocalDateTime updateAt;
    private Long commentId;

    public ReplyCommentRes(ReplyComments comments) {
        this.id = comments.getId();
        this.memberId = comments.getMemberId();
        this.email =comments.getUserEmail();
        this.comments = comments.getComments();
        this.createAt = comments.getCreateAt();
        this.commentId = comments.getCommentId();
    }
}

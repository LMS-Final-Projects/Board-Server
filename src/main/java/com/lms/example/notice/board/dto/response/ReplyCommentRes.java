package com.lms.example.notice.board.dto.response;

import com.lms.example.notice.board.entity.ReplyComments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReplyCommentRes {
    private Long id;
    private String memberId;
    private String comments;
    private LocalDateTime createAt;
    private String memberName;
    private LocalDateTime updateAt;
    private Long commentId;

    public ReplyCommentRes(ReplyComments comments) {
        this.id = comments.getId();
        this.memberId = comments.getMember().getId();
        this.memberName =comments.getMember().getName();
        this.comments = comments.getComments();
        this.createAt = comments.getCreateAt();
        this.commentId = comments.getCommentId();
    }
}

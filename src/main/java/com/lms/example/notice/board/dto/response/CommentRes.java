package com.lms.example.notice.board.dto.response;

import com.lms.example.notice.board.entity.Comments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentRes {
    private Long id;
    private String memberName;
    private String email;
    private String comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private UUID boardId;

    public CommentRes(Comments comments) {
        this.id = comments.getId();
        this.memberName = comments.getMember().getName();
        this.email = comments.getMember().getEmail();
        this.comments = comments.getComments();
        this.createAt = comments.getCreateAt();
        this.updateAt = comments.getUpdateAt();
        this.boardId = comments.getBoardId();
    }

}

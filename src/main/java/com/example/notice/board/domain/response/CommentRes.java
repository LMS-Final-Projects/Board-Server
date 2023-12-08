package com.example.notice.board.domain.response;

import com.example.notice.board.domain.entity.Comments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentRes {
    private Long id;
    private String email;
    private String comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private UUID boardId;

    public CommentRes(Comments comments) {
        this.id = comments.getId();
        this.email = comments.getUserEmail();
        this.comments = comments.getComments();
        this.createAt = comments.getCreateAt();
        this.updateAt = comments.getUpdateAt();
        this.boardId = comments.getBoardId();
    }
}

package com.example.notice.admin.dto;

import com.example.notice.board.domain.entity.Comments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClassCommentDto {

    private Long id;
    private String userId;
    private String comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long classBoardId;

    public ClassCommentDto(Long id, String userId, String comments, LocalDateTime createAt, LocalDateTime updateAt, Long classBoardId) {
        this.id = id;
        this.userId = userId;
        this.comments = comments;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.classBoardId = classBoardId;
    }

    public ClassCommentDto(Comments comments) {
        this.id = comments.getId();
        this.userId = comments.getUserId();
        this.comments = comments.getComments();
        this.createAt = comments.getCreateAt();
        this.updateAt = comments.getUpdateAt();
        this.classBoardId = comments.getClassBoardId();
    }

    public Comments toEntity(){
        return Comments
                .builder()
                .classBoardId(id)
                .userId(userId)
                .comments(comments)
                .createAt(createAt)
                .updateAt(updateAt)
                .id(id)
                .build();
    }
}

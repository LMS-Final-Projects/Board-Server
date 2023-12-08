package com.example.notice.admin.dto;

import com.example.notice.board.domain.entity.ReplyComments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClassReplyCommentDto {
    private Long id;
    private String userId;
    private String comments;
    private LocalDateTime createAt;
    private String userEmail;
    private Long classBoardId;
    private Long classCommentId;


    public ClassReplyCommentDto(Long id, String userId, String comments, LocalDateTime createAt, String userEmail, Long classBoardId, Long classCommentId) {
        this.id = id;
        this.userId = userId;
        this.comments = comments;
        this.createAt = createAt;
        this.userEmail = userEmail;
        this.classBoardId = classBoardId;
        this.classCommentId = classCommentId;
    }

    public ClassReplyCommentDto(ReplyComments replyComments) {
        this.id = replyComments.getId();
        this.userId = replyComments.getUserId();
        this.comments = replyComments.getComments();
        this.createAt = replyComments.getCreateAt();
        this.classBoardId = replyComments.getClassBoardId();
        this.classCommentId = replyComments.getClassCommentId();
    }

    public ReplyComments toEntity(){
       return ReplyComments
               .builder()
               .id(id)
               .userId(userId)
               .comments(comments)
               .createAt(createAt)
               .userEmail(userEmail)
               .classBoardId(classBoardId)
               .classCommentId(classCommentId)
               .build();
    }
}

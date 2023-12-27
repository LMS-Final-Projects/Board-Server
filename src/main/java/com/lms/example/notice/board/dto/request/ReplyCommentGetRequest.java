package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.ReplyComments;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyCommentGetRequest {
    private Long commentId;
    private String comments;

    public ReplyComments toEntity() {
        return ReplyComments
                .builder()
                .commentId(commentId)
                .comments(comments)
                .createAt(LocalDateTime.now())
                .build();
    }
}
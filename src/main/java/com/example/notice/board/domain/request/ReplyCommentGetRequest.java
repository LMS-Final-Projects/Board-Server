package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.ReplyComments;
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
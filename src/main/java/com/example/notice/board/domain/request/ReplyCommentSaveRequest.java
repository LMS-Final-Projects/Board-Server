package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.ReplyComments;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyCommentSaveRequest {
    private String userEmail;
    private Long commentId;
    private String comment;

    public ReplyComments toEntity(){
        return ReplyComments
                .builder()
                .userEmail(userEmail)
                .commentId(commentId)
                .comments(comment)
                .createAt(LocalDateTime.now())
                .build();
    }

}

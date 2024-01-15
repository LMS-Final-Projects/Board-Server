package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.Member;
import com.lms.example.notice.board.entity.ReplyComments;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyCommentSaveRequest {
    private String memberId;
    private Long commentId;
    private String boardId;
    private String comment;

    public ReplyComments toEntity(){
        return ReplyComments
                .builder()
                .commentId(commentId)
                .comments(comment)
                .createAt(LocalDateTime.now())
                .build();
    }

}

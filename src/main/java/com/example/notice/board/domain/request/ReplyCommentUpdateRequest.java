package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.ReplyComments;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyCommentUpdateRequest {
    private String comment;
    private String userEmail;
    private Long replyId;

    public ReplyComments toEntity(){
        return ReplyComments
                .builder()
                .comments("삭제된 댓글입니다.")
                .userEmail("비공개")
                .updateAt(LocalDateTime.now())
                .build();
    }

}
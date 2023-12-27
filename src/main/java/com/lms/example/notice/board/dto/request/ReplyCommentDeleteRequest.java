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
public class ReplyCommentDeleteRequest {

    private Long replyCommentId;
    private String  memberId;

    public ReplyComments toEntity(){
        return ReplyComments
                .builder()
                .comments("삭제된 댓글입니다.")
                .updateAt(LocalDateTime.now())
                .build();
    }

}
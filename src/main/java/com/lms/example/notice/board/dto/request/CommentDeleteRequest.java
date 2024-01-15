package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.Comments;
import com.lms.example.notice.board.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteRequest {
    private String memberId;
    private Long commentId;

    public Comments toEntity(){
       return Comments
               .builder()
               .comments("삭제된 댓글입니다.")
               .updateAt(LocalDateTime.now())
               .build();
    }
}



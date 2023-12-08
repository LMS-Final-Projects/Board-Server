package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.Comments;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteRequest {
    private String userEmail;
    private Long commentId;
    private UUID noticeId;
    private String comment;

    public Comments toEntity(){
       return Comments
               .builder()
               .comments("삭제된 댓글입니다.")
               .userEmail("비공개")
               .updateAt(LocalDateTime.now())
               .build();
    }
}



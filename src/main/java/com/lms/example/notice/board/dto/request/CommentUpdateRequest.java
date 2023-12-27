package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.Comments;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequest {
    private String memberId;
    private UUID boardId;
    private String comment;
    private Long commentId;

    public Comments toEntity(){
       return Comments
               .builder()
               .comments(comment)
               .boardId(boardId)
               .updateAt(LocalDateTime.now())
               .build();
    }
}



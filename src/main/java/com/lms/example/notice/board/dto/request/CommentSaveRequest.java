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
public class CommentSaveRequest {
    private String memberId;
    private UUID boardId;
    private String comment;

    public Comments toEntity(){
       return Comments
               .builder()
               .comments(comment)
               .boardId(boardId)
               .createAt(LocalDateTime.now())
               .build();
    }
}



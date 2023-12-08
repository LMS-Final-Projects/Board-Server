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
public class CommentSaveRequest {
    private String userEmail;
    private UUID boardId;
    private String comment;

    public Comments toEntity(){
       return Comments
               .builder()
               .userEmail(userEmail)
               .comments(comment)
               .boardId(boardId)
               .createAt(LocalDateTime.now())
               .build();
    }
}



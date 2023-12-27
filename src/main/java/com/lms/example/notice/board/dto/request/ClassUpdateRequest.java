package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.ClassBoards;
import com.lms.example.notice.board.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassUpdateRequest {
    private UUID classId;
    private String memberId;
    private String title;

    public ClassBoards toEntity() {
        return ClassBoards.builder()
                .classId(classId)
                .member(Member.builder().id(memberId).build())
                .title(title)
                .updateAt(LocalDateTime.now())
                .build();
    }
}

package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.ClassBoards;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDeleteRequest {
    private UUID classId;
    private UUID memberId;

    public ClassBoards toEntity() {
        return ClassBoards.builder()
                .classId(classId)
                .memberId(memberId)
                .build();
    }
}

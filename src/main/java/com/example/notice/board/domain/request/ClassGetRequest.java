package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.ClassBoards;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassGetRequest {
    private UUID classId;

    public ClassBoards toEntity() {
        return ClassBoards.builder()
                .classId(classId)
                .build();
    }
}

package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.ClassBoards;
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

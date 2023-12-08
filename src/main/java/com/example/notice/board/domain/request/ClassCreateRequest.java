package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.ClassBoards;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassCreateRequest {
    private UUID classId;
    private UUID memberId;
    private String email;
    private String title;
    private LocalDateTime createAt;
    private String fileUrl;

    public ClassBoards toEntity() {
        return ClassBoards.builder()
                .classId(classId)
                .memberId(memberId)
                .email(email)
                .title(title)
                .createAt(LocalDateTime.now())
                .build();
    }
}
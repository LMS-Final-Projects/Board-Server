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
public class ClassCreateRequest {
    private Integer lectureId;
    private String memberId;

    public ClassBoards toEntity() {
        return ClassBoards.builder()
                .lectureId(lectureId)
                .member(Member.builder().id(memberId).build())
                .createAt(LocalDateTime.now())
                .build();
    }
}

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
    private Long lectureId;
    private String memberId;
    private String email;
    private String title;
    private String fileUrl;

    public ClassBoards toEntity() {
        return ClassBoards.builder()
                .lectureId(lectureId)
                .member(Member.builder().id(memberId).build())
                .email(email)
                .title(title)
                .createAt(LocalDateTime.now())
                .build();
    }
}

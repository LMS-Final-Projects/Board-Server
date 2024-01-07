package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.ClassBoards;
import com.lms.example.notice.board.entity.Member;
import com.lms.example.notice.board.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeUpdateRequest {
    private String noticeId;
    private String memberId;
    private String title;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .updateAt(LocalDateTime.now())
                .build();
    }
}

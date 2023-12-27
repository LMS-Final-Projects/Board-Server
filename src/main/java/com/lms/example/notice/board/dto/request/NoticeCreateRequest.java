package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.Member;
import com.lms.example.notice.board.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeCreateRequest {
    private String memberId;
    private String title;
    private String content;
    private List<String> fileUrls;

    public Notice toEntity() {
        return Notice
                .builder()
                .title(title)
                .createAt(LocalDateTime.now())
                .content(content)
                .build();
    }
}
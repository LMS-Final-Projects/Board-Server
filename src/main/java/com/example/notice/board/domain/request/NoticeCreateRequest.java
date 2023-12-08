package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeCreateRequest {
    private String adminId;
    private String email;
    private String title;
    private LocalDateTime createAt;
    private String content;
    private String fileUrl;

    public Notice toEntity() {
        return Notice
                .builder()
                .userId(adminId)
                .email(email)
                .title(title)
                .createAt(createAt)
                .content(content)
                .updateAt(LocalDateTime.now())
                .build();
    }
}
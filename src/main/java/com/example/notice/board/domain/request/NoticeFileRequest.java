package com.example.notice.board.domain.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFileRequest {

    private String adminId;
    private Long noticeId;
    private String fileName;
    private MultipartFile file;
}

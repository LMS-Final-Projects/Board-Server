package com.lms.example.notice.board.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFileRequest {

    private String noticeId;
    private String fileName;
    private MultipartFile file;
}

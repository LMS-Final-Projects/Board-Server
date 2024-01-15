package com.lms.example.notice.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassFileRequest {

    private UUID professorId;
    private Long classId;
    private String fileName;
    private MultipartFile file;
}

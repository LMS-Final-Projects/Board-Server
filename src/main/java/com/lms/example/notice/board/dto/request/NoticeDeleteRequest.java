package com.lms.example.notice.board.dto.request;

import com.lms.example.notice.board.entity.Notice;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDeleteRequest {
    private List<String> noticeIds;
    public List<Notice> toEntity() {
        return noticeIds.stream()
                .map(id -> Notice.builder().build())
                .collect(Collectors.toList());
    }

}
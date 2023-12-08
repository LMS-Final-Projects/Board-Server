package com.example.notice.board.domain.request;

import com.example.notice.board.domain.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDeleteRequest {
    private List<Long> noticeIds;
    public List<Notice> toEntity() {
        return noticeIds.stream()
                .map(id -> Notice.builder().id(id).build())
                .collect(Collectors.toList());
    }

}
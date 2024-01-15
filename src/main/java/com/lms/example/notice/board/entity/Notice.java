package com.lms.example.notice.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID noticeId;

    private String email;

    private String title;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column
    private String content;

    @OneToMany(mappedBy = "notice")
    private List<NoticeFiles> noticeFiles;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void setNoticeFiles(List<NoticeFiles> noticeFiles) {
        this.noticeFiles = noticeFiles;
    }
}

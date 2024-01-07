package com.lms.example.notice.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReplyComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long commentId;
    private UUID boardId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}

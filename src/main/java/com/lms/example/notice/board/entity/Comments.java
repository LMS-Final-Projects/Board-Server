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
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Column
    private UUID boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;



}

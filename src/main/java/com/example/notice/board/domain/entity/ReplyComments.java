package com.example.notice.board.domain.entity;

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
    private UUID memberId;
    private String comments;
    private String userEmail;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long commentId;

}

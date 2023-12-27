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
public class ClassBoards {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID classId;
    private String email;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long lectureId;

    @OneToMany(mappedBy = "classBoards")
    private List<ClassFiles> classFiles;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}

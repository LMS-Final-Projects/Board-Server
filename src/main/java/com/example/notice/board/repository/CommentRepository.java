package com.example.notice.board.repository;

import com.example.notice.admin.dto.NoticeCommentDto;
import com.example.notice.board.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT n from Comments as n WHERE n.boardId = :noticeId ")
    Optional<List<Comments>> findByNoticeId(@Param("noticeId") UUID noticeId);

    @Query("SELECT n from Comments as n WHERE n.boardId = :boardId ")
    Optional<List<Comments>> findByBoardId(@Param("boardId") UUID boardId);

    @Query("SELECT n from Comments as n WHERE n.id = :id and n.userEmail = :userEmail")
    Optional<Comments> findByUserEmail(@Param("id") Long id, @Param("userEmail") String userEmail);
}

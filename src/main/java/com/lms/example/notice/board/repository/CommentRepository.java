package com.lms.example.notice.board.repository;

import com.lms.example.notice.board.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT c from Comments as c WHERE c.boardId = :id ")
    Optional<List<Comments>> findById(@Param("id") UUID id);

    @Query("SELECT n from Comments as n WHERE n.boardId = :boardId ")
    Optional<List<Comments>> findByBoardId(@Param("boardId") UUID boardId);

    @Query("SELECT n from Comments as n WHERE n.id = :id and n.member.id = :memberId")
    Optional<Comments> findByMemberId(@Param("id") Long id, @Param("memberId") String memberId);
}

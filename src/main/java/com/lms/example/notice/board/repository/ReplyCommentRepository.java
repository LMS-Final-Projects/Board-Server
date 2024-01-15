package com.lms.example.notice.board.repository;

import com.lms.example.notice.board.entity.ReplyComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReplyCommentRepository extends JpaRepository<ReplyComments, Long> {


    @Query("SELECT r from ReplyComments as r WHERE r.boardId = :boardId")
    List<ReplyComments> findByBoardId( @Param("boardId")UUID boardId );

    @Query("SELECT c from ReplyComments as c WHERE c.id = :id and c.member.id = :memberId")
    Optional<ReplyComments> findByMemberId(@Param("id") Long id, @Param("memberId") String memberId);
}

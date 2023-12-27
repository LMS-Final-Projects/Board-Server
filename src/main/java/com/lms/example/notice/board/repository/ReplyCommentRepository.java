package com.lms.example.notice.board.repository;

import com.lms.example.notice.board.entity.ReplyComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyCommentRepository extends JpaRepository<ReplyComments, Long> {


    @Query("SELECT c from ReplyComments as c WHERE c.commentId = :commentId")
    List<ReplyComments> findByCommentId( @Param("commentId") Long commentId );

    @Query("SELECT c from ReplyComments as c WHERE c.id = :id and c.member.id = :memberId")
    Optional<ReplyComments> findByMemberId(@Param("id") Long id, @Param("memberId") String memberId);
}

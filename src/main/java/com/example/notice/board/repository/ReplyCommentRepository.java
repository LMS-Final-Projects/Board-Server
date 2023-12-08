package com.example.notice.board.repository;

import com.example.notice.admin.dto.ClassReplyCommentDto;
import com.example.notice.admin.dto.NoticeReplyCommentDto;
import com.example.notice.board.domain.entity.Comments;
import com.example.notice.board.domain.entity.ReplyComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReplyCommentRepository extends JpaRepository<ReplyComments, Long> {


    @Query("SELECT c from ReplyComments as c WHERE c.commentId = :commentId")
    List<ReplyComments> findByCommentId( @Param("commentId") Long commentId );

    @Query("SELECT c from ReplyComments as c WHERE c.id = :id and c.userEmail = :userEmail")
    Optional<ReplyComments> findByUserEmail(@Param("id") Long id, @Param("userEmail") String userEmail);
}

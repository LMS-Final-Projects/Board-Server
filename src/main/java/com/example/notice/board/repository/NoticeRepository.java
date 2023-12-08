package com.example.notice.board.repository;

import com.example.notice.admin.dto.NoticeDto;
import com.example.notice.board.domain.entity.Notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoticeRepository
        extends JpaRepository<Notice, Long> {

    @Query("SELECT n from Notice as n where n.memberId = :memberId" )
    Optional<Notice> findNoticeByMemberId(@Param("memberId")UUID memberId);

    @Modifying
    @Query("DELETE FROM Notice n WHERE n.noticeId = :noticeId")
    void deleteNoticeById(@Param("noticeId") UUID noticeId);
}

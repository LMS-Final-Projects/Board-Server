package com.lms.example.notice.board.repository;

import com.lms.example.notice.board.entity.Notice;
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
        extends JpaRepository<Notice, UUID> {

    @Query("delete from  Notice as n where n.noticeId IN :noticeIds")
    void deleteNoticeByNoticeIds(@Param("noticeIds") List<String> noticeIds);

    @Query("SELECT  n from  Notice as n where n.noticeId IN :noticeIds")
    List<Notice> findNoticeByNoticeIds(@Param("noticeIds") List<String> noticeIds);

    @Query("SELECT n from Notice as n where n.member.id = :memberId" )
    Optional<Notice> findNoticeByMemberId(@Param("memberId")String memberId);

    @Modifying
    @Query("DELETE FROM Notice n WHERE n.noticeId = :noticeId")
    void deleteNoticeById(@Param("noticeId") UUID noticeId);
}

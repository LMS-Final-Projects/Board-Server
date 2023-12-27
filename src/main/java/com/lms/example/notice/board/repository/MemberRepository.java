package com.lms.example.notice.board.repository;
import com.lms.example.notice.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("SELECT m from Member as m where m.id = :id")
    Member findByUUId(@Param("id") UUID id);
}

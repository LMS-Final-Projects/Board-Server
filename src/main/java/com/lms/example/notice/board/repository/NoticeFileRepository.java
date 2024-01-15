package com.lms.example.notice.board.repository;

import com.lms.example.notice.board.entity.ClassBoards;
import com.lms.example.notice.board.entity.NoticeFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoticeFileRepository
        extends JpaRepository<NoticeFiles, Long> {





}

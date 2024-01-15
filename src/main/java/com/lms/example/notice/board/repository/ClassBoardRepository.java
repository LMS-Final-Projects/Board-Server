package com.lms.example.notice.board.repository;

import com.lms.example.notice.board.entity.ClassBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassBoardRepository
        extends JpaRepository<ClassBoards, UUID> {


    @Query("DELETE from ClassBoards as c WHERE c.classId = :id")
    void deleteByClassId(@Param("id") UUID id);


    @Query("SELECT c from ClassBoards as c WHERE c.lectureId = :id")
    Optional<ClassBoards> findByLectureId(@Param("id") Integer id);


}

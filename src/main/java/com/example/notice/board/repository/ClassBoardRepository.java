package com.example.notice.board.repository;

import com.example.notice.admin.dto.CLassDto;

import com.example.notice.board.domain.entity.ClassBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassBoardRepository
        extends JpaRepository<ClassBoards, Long> {


    @Query("DELETE from ClassBoards as c WHERE c.classId = :id")
    void deleteByClassId(@Param("id") UUID id);


    @Query("SELECT c from ClassBoards as c WHERE c.classId = :id")
    Optional<ClassBoards> findByClassId(@Param("id") UUID id);

    @Query("select c from ClassBoards as c where c.memberId = :userId and c.id = :classId")
    Optional<ClassBoards> findByUserIdandClassId(@Param("userId") String userId, @Param("classId")Long classId);

}

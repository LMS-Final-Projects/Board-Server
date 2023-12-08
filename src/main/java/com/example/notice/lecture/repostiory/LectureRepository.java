package com.example.notice.lecture.repostiory;

import com.example.notice.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository
        extends JpaRepository<Lecture,Long> {
}

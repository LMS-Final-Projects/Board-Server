package com.lms.example.notice.board.controller;


import com.lms.example.global.response.LmsResponse;
import com.lms.example.notice.board.dto.response.ClassBoardRes;
import com.lms.example.notice.board.dto.response.NoticeRes;
import com.lms.example.notice.board.service.BoardService;
import com.lms.example.notice.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final NoticeService noticeService;
    private final BoardService boardService;

    //공지사항 전체 보기
    @GetMapping("/notices")
    public LmsResponse<List<NoticeRes>> getAllNotices(){
        List<NoticeRes> allNotices = noticeService.getAllNotices();
        return new LmsResponse<>(HttpStatus.OK, allNotices, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 전체 보기
    @GetMapping("/classes")
    public LmsResponse<List<ClassBoardRes>> getAllBoards(){
        List<ClassBoardRes> allBoards = boardService.getAllBoards();
        return new LmsResponse<>(HttpStatus.OK, allBoards, "서비스 성공", "", LocalDateTime.now());
    }
}

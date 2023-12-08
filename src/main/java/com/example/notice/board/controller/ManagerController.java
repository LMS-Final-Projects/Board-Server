package com.example.notice.board.controller;


import com.example.global.response.LmsResponse;
import com.example.notice.board.domain.response.NoticeRes;
import com.example.notice.board.service.BoardService;
import com.example.notice.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    //공지사항 전체 보기
    @GetMapping("/notices")
    public LmsResponse<List<NoticeRes>> getAllNotices(){
        List<NoticeRes> allNotices = noticeService.getAllNotices();
        return new LmsResponse<>(HttpStatus.OK, allNotices, "서비스 성공", "", LocalDateTime.now());
    }
}

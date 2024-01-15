package com.lms.example.notice.board.controller;


import com.lms.example.global.response.LmsResponse;

import com.lms.example.notice.board.dto.request.*;
import com.lms.example.notice.board.dto.response.NoticeRes;
import com.lms.example.notice.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항 생성
    @PostMapping
    public LmsResponse<NoticeRes> createNotice(@RequestBody NoticeCreateRequest noticeCreateRequest){
        NoticeRes noticeRes = noticeService.writeNotice(noticeCreateRequest);
        return new LmsResponse<>(HttpStatus.OK, noticeRes, "서비스 성공", "", LocalDateTime.now());
    }

    //공지사항 수정
    @PostMapping("/info")
    public LmsResponse<NoticeRes> updateNotice(@RequestBody NoticeUpdateRequest noticeUpdateRequest){
        NoticeRes noticeRes = noticeService.updateNotice(noticeUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, noticeRes, "서비스 성공", "", LocalDateTime.now());
    }


    //공지사항 보기
    @GetMapping("/{id}")
    public LmsResponse<NoticeRes> getNotice(@PathVariable("id")  UUID id){
        NoticeRes noticeRes = noticeService.getNotice(id);
        return new LmsResponse<>(HttpStatus.OK, noticeRes, "서비스 성공", "", LocalDateTime.now());

    }

    //공지사항삭제
    @PostMapping("/delete")
    public LmsResponse<Void> deleteNotice(@RequestBody NoticeDeleteRequest noticedeleteRequest){
        noticeService.deleteNotice(noticedeleteRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }


    //공지사항 파일 업로드
    @PostMapping("/uploadNoticeFile")

    public LmsResponse<Void> uploadNoticeFile(
            @ModelAttribute NoticeFileRequest noticeFileRequest
    ) {
        noticeService.uploadNoticeFile(noticeFileRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }


    //공지사항 파일 다운로드
    @PostMapping("/downloadNoticeFile")
    public LmsResponse<FileSystemResource> downloadNoticeFile(@RequestBody NoticeFileRequest noticeFileRequest) {
        FileSystemResource noticeFile = noticeService.downloadNoticeFile(noticeFileRequest);
        return new LmsResponse<>(HttpStatus.OK, noticeFile, "서비스 성공", "", LocalDateTime.now());
    }

    //공지사항 파일 목록 가져오기
    @PostMapping("/getNoticeFile")
    public LmsResponse<List<String>> getNoticeFile(@RequestBody NoticeGetFileRequest request) {
        List<String> noticeFile = noticeService.getNoticeFile(request);
        return new LmsResponse<>(HttpStatus.OK, noticeFile, "서비스 성공", "", LocalDateTime.now());
    }



    //공지사항 파일 삭제
    @PostMapping("/deleteNoticeFile")
    public  LmsResponse<Boolean> deleteNoticeFile(@RequestBody NoticeFileRequest noticeFileRequest){
        boolean b = noticeService.deleteNoticeFile(noticeFileRequest);
        return new LmsResponse<>(HttpStatus.OK, b, "서비스 성공", "", LocalDateTime.now());
    }



}

package com.example.notice.board.controller;


import com.example.global.response.LmsResponse;
import com.example.notice.board.domain.request.*;
import com.example.notice.board.domain.response.*;
import com.example.notice.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/notices")
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
    public LmsResponse<NoticeRes> updateNotice(@RequestParam UUID memberId){
        NoticeRes noticeRes = noticeService.updateNotice(memberId);
        return new LmsResponse<>(HttpStatus.OK, noticeRes, "서비스 성공", "", LocalDateTime.now());
    }


    //공지사항 보기
    @GetMapping("/getNotice/{id}")
    public LmsResponse<NoticeRes> getNotice(@PathVariable("id")  Long id){
        NoticeRes noticeRes = noticeService.getNotice(id);
        return new LmsResponse<>(HttpStatus.OK, noticeRes, "서비스 성공", "", LocalDateTime.now());

    }

    //공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public LmsResponse<Void> deleteNotice(@RequestParam UUID memberId, @RequestParam UUID noticeId){
        noticeService.deleteNotice(memberId,noticeId);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }


    //공지사항 파일 업로드
    @PostMapping("/uploadNoticeFile")

    public LmsResponse<String> uploadNoticeFile(
            @ModelAttribute NoticeFileRequest noticeFileRequest
    ) {
        String s = noticeService.uploadNoticeFile(noticeFileRequest);
        return new LmsResponse<>(HttpStatus.OK, s, "서비스 성공", "", LocalDateTime.now());
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

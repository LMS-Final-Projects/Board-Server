package com.example.notice.board.controller;


import com.example.global.response.LmsResponse;
import com.example.notice.board.domain.request.*;
import com.example.notice.board.domain.response.CommentRes;
import com.example.notice.board.service.BoardService;
import com.example.notice.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final NoticeService noticeService;
    private final BoardService boardService;


    //공지사항 댓글 보기
    @GetMapping("/{noticeId}")
    public LmsResponse<List<CommentRes>> getNoticeComments(@PathVariable("id") UUID noticeId){
        List<CommentRes> noticeComments = noticeService.getNoticeComments(noticeId);
        return new LmsResponse<>(HttpStatus.OK, noticeComments, "서비스 성공", "", LocalDateTime.now());

    }

    //공지사항 댓글 작성
    @PostMapping("/notices")
    public LmsResponse<CommentRes> writeNoticeComments(@RequestBody CommentSaveRequest commentSaveRequest){
        CommentRes commentRes = noticeService.writeNoticeComments(commentSaveRequest);
        return new LmsResponse<>(HttpStatus.OK, commentRes, "서비스 성공", "", LocalDateTime.now());
    }


    //공지사항 댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @PostMapping("/notices/delete")
    public LmsResponse<String> deleteNoticeComments(@RequestBody CommentDeleteRequest commentDeleteRequest){
        noticeService.deleteNoticeComments(commentDeleteRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }

    //공지사항 댓글 수정
    @PostMapping("/notices/info")
    public LmsResponse<Void> uploadNoticeComments(@RequestBody CommentUpdateRequest commentUpdateRequest){
        noticeService.uploadNoticeComments(commentUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 게시판 댓글 보기
    @GetMapping("/{boardId}")
    public LmsResponse<List<CommentRes>> getClassComments(@PathVariable("id") UUID boardId){
        List<CommentRes> classComments = boardService.getClassComments(boardId);
        return new LmsResponse<>(HttpStatus.OK, classComments , "서비스 성공", "", LocalDateTime.now());
    }


    //강의 게시판 댓글 작성
    @PostMapping("/boards")
    public LmsResponse<CommentRes> writeClassComments(@RequestBody CommentSaveRequest commentSaveRequest){
        CommentRes commentRes = boardService.writeClassComments(commentSaveRequest);
        return new LmsResponse<>(HttpStatus.OK, commentRes, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 게시판 댓글 수정
    @PostMapping("/boards/info")
    public LmsResponse<Void> updateClassComments(@RequestBody CommentUpdateRequest commentUpdateRequest){
        boardService.updateClassComments(commentUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }



    //강의 게시판 댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @PostMapping("/boards/delete")
    public LmsResponse<Void> deleteClassComments(@RequestBody CommentDeleteRequest commentDeleteRequest){
        boardService.deleteClassComments(commentDeleteRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "에러 없음", LocalDateTime.now());
    }

}

package com.example.notice.board.controller;


import com.example.global.response.LmsResponse;
import com.example.notice.board.domain.request.*;
import com.example.notice.board.domain.response.ReplyCommentRes;
import com.example.notice.board.service.BoardService;
import com.example.notice.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final NoticeService noticeService;
    private final BoardService boardService;

    //공지사항 대댓글 보기
    @GetMapping("/notices/{commentId}")
    public LmsResponse<List<ReplyCommentRes>> getNoticeReplyComments(@PathVariable("commentId")Long commentId){
        List<ReplyCommentRes> noticeReplyComments = noticeService.getNoticeReplyComments(commentId);
        return new LmsResponse<>(HttpStatus.OK, noticeReplyComments, "서비스 성공", "", LocalDateTime.now());

    }

    //공지 사항 대댓글 작성
    @PostMapping("/notices")
    public LmsResponse<ReplyCommentRes> writeNoticeReplyComments(@RequestBody ReplyCommentSaveRequest replyCommentSaveRequest){
        ReplyCommentRes replyCommentRes = noticeService.writeNoticeReplyComments(replyCommentSaveRequest);
        return new LmsResponse<>(HttpStatus.OK, replyCommentRes, "서비스 성공", "", LocalDateTime.now());
    }

    //공지사항 대댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @PostMapping("/notices/delete")
    public LmsResponse<String> deleteNoticeReplyComments(@RequestBody ReplyCommentDeleteRequest replyCommentDeleteRequest){
        String s = noticeService.deleteNoticeReplyComments(replyCommentDeleteRequest);
        return new LmsResponse<>(HttpStatus.OK, s, "서비스 성공", "", LocalDateTime.now());
    }

    //공지사항 대댓글 수정
    @PostMapping("/notices/info")
    public LmsResponse<Void> uploadNoticeReplyComments(@RequestBody ReplyCommentUpdateRequest replyCommentUpdateRequest){
        noticeService.uploadNoticeReplyComments(replyCommentUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 게시판 대댓글 보기
    @GetMapping("/boards/{commentId}")
    public LmsResponse<List<ReplyCommentRes>> getClassReplyComments(@PathVariable("commentId")Long commentId){
        List<ReplyCommentRes> classReplyComments = boardService.getClassReplyComments(commentId);
        return new LmsResponse<>(HttpStatus.OK, classReplyComments, "서비스 성공", "에러 없음", LocalDateTime.now());

    }
    //강의 게시판 대댓글 작성
    @PostMapping("/boards")
    public LmsResponse<ReplyCommentRes> writeClassReplyComments(@RequestBody ReplyCommentSaveRequest replyCommentSaveRequest){
        ReplyCommentRes classReplyCommentRes = boardService.writeClassReplyComments(replyCommentSaveRequest);
        return new LmsResponse<>(HttpStatus.OK, classReplyCommentRes, "서비스 성공", "에러 없음", LocalDateTime.now());
    }

    //강의 게시판 대댓글 수정
    @PostMapping("/boards/info")
    public LmsResponse<Void> updateClassReplyComments(@RequestBody ReplyCommentUpdateRequest replyCommentUpdateRequest){
        boardService.updateClassReplyComments(replyCommentUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "에러 없음", LocalDateTime.now());
    }

    //강의 게시판 대댓글 삭제
    @PostMapping("/boards/delete")
    public LmsResponse<Void> deleteClassReplyComments(@RequestBody ReplyCommentDeleteRequest replyCommentDeleteRequest){
        boardService.deleteClassReplyComments(replyCommentDeleteRequest);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "에러 없음", LocalDateTime.now());
    }
}

package com.lms.example.notice.board.controller;


import com.lms.example.global.response.LmsResponse;
import com.lms.example.notice.board.dto.request.ClassCreateRequest;
import com.lms.example.notice.board.dto.request.ClassFileRequest;
import com.lms.example.notice.board.dto.request.ClassUpdateRequest;
import com.lms.example.notice.board.dto.response.ClassBoardRes;
import com.lms.example.notice.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //강의 게시판 보기
    @GetMapping("/{id}")
    public LmsResponse<ClassBoardRes> getClass(@PathVariable("id") UUID id){
        ClassBoardRes aClass = boardService.getClass(id);
        return new LmsResponse<>(HttpStatus.OK, aClass, "서비스 성공", "", LocalDateTime.now());

    }

    //강의게시판 생성
    @PostMapping
    public LmsResponse<ClassBoardRes> createClass(@RequestBody ClassCreateRequest classCreateRequest){
        ClassBoardRes classBoardRes = boardService.writeClass(classCreateRequest);
        return new LmsResponse<>(HttpStatus.OK, classBoardRes, "서비스 성공", "", LocalDateTime.now());
    }

    //강의게시판 정보변경
    @PostMapping("/info")
    public LmsResponse<ClassBoardRes> updateClass(@RequestBody ClassUpdateRequest classUpdateRequest){
        ClassBoardRes classBoardRes = boardService.updateClass(classUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, classBoardRes, "서비스 성공", "", LocalDateTime.now());
    }


    //강의 파일 보기
    @GetMapping("/getClassFile")
    public LmsResponse<List<String>> getClassFile(@RequestBody ClassFileRequest request){
        List<String> classFile = boardService.getClassFile(request);
        return new LmsResponse<>(HttpStatus.OK, classFile, "서비스 성공", "", LocalDateTime.now());

    }
    //강의 파일 업로드
    @PostMapping("/uploadClassFile")
    public LmsResponse<String> uploadClassFile(@RequestBody ClassFileRequest classFileRequest){
        String s = boardService.uploadClassFile(classFileRequest);
        return new LmsResponse<>(HttpStatus.OK, s, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 파일 다운로드
    @PostMapping("/downloadClassFile")
    public LmsResponse<FileSystemResource> downloadClassFile(@RequestBody ClassFileRequest request) {
        FileSystemResource noticeFile = boardService.downloadClassFile(request);
        return new LmsResponse<>(HttpStatus.OK, noticeFile, "서비스 성공", "", LocalDateTime.now());
    }


    //강의 파일 삭제
    @PostMapping("/deleteClassFile")
    public LmsResponse<Boolean> deleteClassFile(@RequestBody ClassFileRequest classFileRequest){
        boolean b = boardService.deleteClassFile(classFileRequest);
        return new LmsResponse<>(HttpStatus.OK, b, "서비스 성공", "", LocalDateTime.now());
    }



}

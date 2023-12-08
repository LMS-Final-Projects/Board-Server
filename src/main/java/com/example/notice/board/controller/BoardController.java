package com.example.notice.board.controller;


import com.example.global.response.LmsResponse;
import com.example.notice.board.domain.request.*;
import com.example.notice.board.domain.response.*;
import com.example.notice.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //강의 게시판 보기
    @GetMapping("/{classId}")
    public LmsResponse<ClassBoardRes> getClass(@PathVariable("classId") UUID classId){
        ClassBoardRes aClass = boardService.getClass(classId);
        return new LmsResponse<>(HttpStatus.OK, aClass, "서비스 성공", "", LocalDateTime.now());

    }

    //강의게시판 생성
    @PostMapping
    public LmsResponse<ClassBoardRes> createClass(@RequestBody ClassCreateRequest classCreateRequest){
        ClassBoardRes classBoardRes = boardService.writeClass(classCreateRequest);
        return new LmsResponse<>(HttpStatus.OK, classBoardRes, "서비스 성공", "", LocalDateTime.now());
    }

    //강의게시판 정보변경
    @PostMapping
    public LmsResponse<ClassBoardRes> updateClass(@RequestBody ClassUpdateRequest classUpdateRequest){
        ClassBoardRes classBoardRes = boardService.updateClass(classUpdateRequest);
        return new LmsResponse<>(HttpStatus.OK, classBoardRes, "서비스 성공", "", LocalDateTime.now());
    }


    //강의 게시판 삭제
    @DeleteMapping("/{classId}")
    public LmsResponse<String> deleteClass(@PathVariable("classId") UUID classId){
        boardService.deleteClass(classId);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
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

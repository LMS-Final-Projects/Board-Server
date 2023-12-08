package com.example.notice.board.service;

import com.example.global.exception.ClientException;
import com.example.global.exception.MethodException;
import com.example.global.exception.NotFoundException;
import com.example.notice.board.domain.entity.ClassBoards;
import com.example.notice.board.domain.entity.Comments;
import com.example.notice.board.domain.entity.ReplyComments;
import com.example.notice.board.domain.request.*;
import com.example.notice.board.domain.response.ClassBoardRes;
import com.example.notice.board.domain.response.CommentRes;
import com.example.notice.board.domain.response.ReplyCommentRes;
import com.example.notice.board.repository.ClassBoardRepository;
import com.example.notice.board.repository.CommentRepository;
import com.example.notice.board.repository.ReplyCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final ClassBoardRepository classBoardRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;

    private final String fileDirectory = "C:/LmsFile";


    //강의 게시판 보기
    @Transactional
    public ClassBoardRes getClass(UUID classId){
        ClassBoards classBoards = classBoardRepository.findByClassId(classId).orElseThrow(() -> new NotFoundException("게시판이 없습니다."));
        ClassBoardRes classBoardRes = new ClassBoardRes(classBoards);
        return classBoardRes;
    }

    //강의게시판 생성
    @Transactional
    public ClassBoardRes writeClass(ClassCreateRequest classCreateRequest){
        ClassBoards save = classBoardRepository.save(classCreateRequest.toEntity());
        ClassBoardRes classBoardRes = new ClassBoardRes(save);
        return classBoardRes;
    }

    //강의게시판 생성
    @Transactional
    public ClassBoardRes updateClass(ClassUpdateRequest classCreateRequest){
        ClassBoards save = classBoardRepository.save(classCreateRequest.toEntity());
        ClassBoardRes classBoardRes = new ClassBoardRes(save);
        return classBoardRes;
    }


    //강의 게시판 삭제
    @Transactional
    public void deleteClass(UUID classId){;
        ClassBoards classBoards = classBoardRepository.findByClassId(classId).orElseThrow(() -> new NotFoundException("게시판이 없습니다."));
        classBoardRepository.deleteByClassId(classBoards.getClassId());
    }


    //강의 파일 다운
    @Transactional
    public FileSystemResource downloadClassFile(ClassFileRequest classFileRequest) {
        try {
            String fileName = classFileRequest.getFileName();
            Path filePath = Paths.get(fileDirectory, fileName);
            File file = filePath.toFile();

            if (file.exists()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

                FileSystemResource response = new FileSystemResource(file);

                return response;
            } else {
                throw new NotFoundException("파일이 없습니다.");
            }
        } catch (Exception e) {
            throw new ClientException("클라이언트 오류");
        }
    }

    //강의 파일 가져오기
    public List<String> getClassFile(ClassFileRequest request){

        // 강의 파일 가져오기 로직을 활용하여 파일 목록 가져오기
        List<String> allFiles = getClassFiles();

        // noticeId가  파일 필터링
        List<String> filteredFiles = allFiles.stream()
                .filter(fileName -> fileName.contains("_" + request.getClassId() + "_"))
                .collect(Collectors.toList());

        return filteredFiles;
    }


    //강의 파일 업로드
    public String uploadClassFile(ClassFileRequest request) {
        try {
            // 저장할 디렉토리 생성
            Files.createDirectories(Path.of(fileDirectory));

            // 파일 경로에 noticeId를 포함시킴
            String fileName = generateClassFileName(request);
            Path filePath = Path.of(fileDirectory, fileName);

            // 파일 저장
            Files.copy(request.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "File uploaded Success!";
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    // 강의 파일 삭제
    public boolean deleteClassFile(ClassFileRequest request) {
        try {
            // 파일명 생성
            String fileName = generateClassFileName(request);

            // 파일 경로 생성
            Path filePath = Path.of(fileDirectory, fileName);

            // 파일 삭제
            boolean isDeleted = Files.deleteIfExists(filePath);

            if (isDeleted) {
                return true;
            } else {
                throw new ClientException("잘못된 파일명입니다:" + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage(), e);
        }
    }

    //강의 게시판 댓글 조회
    @Transactional
    public List<CommentRes> getClassComments(UUID boardId){
        List<Comments> commentsList = commentRepository.findByBoardId(boardId).get();
        List<CommentRes> commentResList = new ArrayList<>();

        for(Comments comments : commentsList) {
            CommentRes commentRes = new CommentRes(comments);
            commentResList.add(commentRes);
        }
        return commentResList;
    }




    //강의 게시판 댓글 작성
    @Transactional
    public CommentRes writeClassComments(CommentSaveRequest commentSaveRequest){
        Comments save = commentRepository.save(commentSaveRequest.toEntity());
        CommentRes classCommentRes = new CommentRes(save);
        return classCommentRes;
    }

    //강의 게시판 댓글 수정
    @Transactional
    public void updateClassComments(CommentUpdateRequest commentUpdateRequest){
        changeClassComment(commentUpdateRequest);
    }

    //강의 게시판 댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @Transactional
    public void deleteClassComments(CommentDeleteRequest commentDeleteRequest){
        changeNullClassComment(commentDeleteRequest);
    }


    //강의 게시판 대댓글 보기
    @Transactional
    public List<ReplyCommentRes> getClassReplyComments(Long commentId){
        List<ReplyComments> byCommentId = replyCommentRepository.findByCommentId(commentId);
        List<ReplyCommentRes> replyCommentList = new ArrayList<>();

        for(ReplyComments replyComment : byCommentId) {
            ReplyCommentRes replyCommentRes = new ReplyCommentRes(replyComment);
            replyCommentList.add(replyCommentRes);
        }

        return replyCommentList;
    }


    //강의 게시판 대댓글 작성
    @Transactional
    public ReplyCommentRes writeClassReplyComments(ReplyCommentSaveRequest replyCommentSaveRequest){
        ReplyComments save = replyCommentRepository.save(replyCommentSaveRequest.toEntity());
        ReplyCommentRes classReplyCommentRes = new ReplyCommentRes(save);
        return classReplyCommentRes;
    }

    //강의 게시판 대댓글 수정
    @Transactional
    public void updateClassReplyComments(ReplyCommentUpdateRequest replyCommentUpdateRequest){
        changeClassReplyComment(replyCommentUpdateRequest);
    }

    //강의 게시판 대댓글 삭제
    @Transactional
    public void deleteClassReplyComments(ReplyCommentDeleteRequest replyCommentDeleteRequest){
        changeNullClassReplyComment(replyCommentDeleteRequest);
    }



    @Transactional
    public void changeClassComment(CommentUpdateRequest commentUpdateRequest){
        try {
            commentRepository.findByUserEmail(commentUpdateRequest.getCommentId(), commentUpdateRequest.getUserEmail()).orElseThrow(()-> new NotFoundException("수정할 댓글이 없습니다.") );
            commentRepository.save(commentUpdateRequest.toEntity());
        }catch (Exception e) {
            throw new MethodException("댓글 수정 실패");
        }
    }


    @Transactional
    public void changeNullClassComment(CommentDeleteRequest commentDeleteRequest){
        try {
            commentRepository.findByUserEmail(commentDeleteRequest.getCommentId(), commentDeleteRequest.getUserEmail()).orElseThrow(() -> new NotFoundException("삭제할 댓글이 없습니다."));
            commentRepository.save(commentDeleteRequest.toEntity());
        }catch (Exception e) {
            throw new MethodException("댓글 삭제 실패");
        }
    }

    @Transactional
    public void changeClassReplyComment(ReplyCommentUpdateRequest replyCommentUpdateRequest){
        try {
            replyCommentRepository.findByUserEmail(replyCommentUpdateRequest.getReplyId(), replyCommentUpdateRequest.getUserEmail()).orElseThrow(() -> new NotFoundException("수정할 댓글이 없습니다."));
            replyCommentRepository.save(replyCommentUpdateRequest.toEntity());
        }catch (Exception e) {
            throw new MethodException("대댓글 수정 실패");
        }
    }


    @Transactional
    public void changeNullClassReplyComment(ReplyCommentDeleteRequest replyCommentDeleteRequest) {
        try {

            replyCommentRepository.findByUserEmail(replyCommentDeleteRequest.getReplyCommentId(), replyCommentDeleteRequest.getUserEmail()).orElseThrow(() -> new NotFoundException("삭제할 댓글이 없습니다."));
            replyCommentRepository.save(replyCommentDeleteRequest.toEntity());
        } catch (Exception e) {
            throw new MethodException("대댓글 삭제 실패");
        }
    }


    private String generateClassFileName(ClassFileRequest request) {
        // 파일명 규칙: classId_originalFilename
        return String.format("%02d_%s",
                request.getClassId(),
                request.getFileName());
    }

    private List<String> getClassFiles(){
        File directory = new File(fileDirectory);
        File[] files = directory.listFiles();

        if (files != null) {
            return Arrays.stream(files)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}

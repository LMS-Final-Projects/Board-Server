package com.example.notice.board.service;

import com.example.global.exception.ClientException;
import com.example.global.exception.MethodException;
import com.example.global.exception.NotFoundException;
import com.example.notice.admin.dto.NoticeReplyCommentDto;
import com.example.notice.board.domain.entity.Comments;
import com.example.notice.board.domain.entity.Notice;
import com.example.notice.board.domain.entity.ReplyComments;
import com.example.notice.board.domain.request.*;
import com.example.notice.board.domain.response.CommentRes;
import com.example.notice.board.domain.response.NoticeRes;
import com.example.notice.board.domain.response.ReplyCommentRes;
import com.example.notice.board.repository.CommentRepository;
import com.example.notice.board.repository.NoticeRepository;
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
public class NoticeService {

    private final NoticeRepository adminBoardRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;

    private final String fileDirectory = "C:/LmsFile";


    //공지사항 전체 조회
    @Transactional
    public List<NoticeRes> getAllNotices(){
        try {
            List<Notice> all = adminBoardRepository.findAll();

            List<NoticeRes> noticeResList = new ArrayList<>();

            for (Notice notice : all) {
                NoticeRes noticeRes = new NoticeRes(notice);
                noticeResList.add(noticeRes);
            }
            return noticeResList;
        } catch (Exception e) {
            throw new MethodException("");
        }
    }


    //공지사항 보기
    @Transactional
    public NoticeRes getNotice(Long id){
        Notice notice = adminBoardRepository.findById(id).orElseThrow(() -> new NotFoundException("공지사항이 없습니다."));
        NoticeRes noticeRes = new NoticeRes(notice);
        return  noticeRes;
    }

    //공지사항 생성
    @Transactional
    public NoticeRes writeNotice(NoticeCreateRequest noticeCreateRequest){
        try {
            Notice save = adminBoardRepository.save(noticeCreateRequest.toEntity());
            NoticeRes noticeRes = new NoticeRes(save);
            return noticeRes;
        }catch (Exception e) {
            throw new MethodException("");
        }
    }

    //공지사항 수정
    @Transactional
    public NoticeRes updateNotice(UUID memberId) {
        Notice notice = adminBoardRepository.findNoticeByMemberId(memberId).orElseThrow(() -> new NotFoundException("공지사항이 없습니다."));
        Notice save = adminBoardRepository.save(notice);
        NoticeRes dto = new NoticeRes(save);
        return dto;
    }

    //공지사항 삭제
    @Transactional
    public void deleteNotice(UUID memberId, UUID noticeId){
        adminBoardRepository.findNoticeByMemberId(memberId).orElseThrow(() -> new NotFoundException("공지사항이 없습니다."));
        adminBoardRepository.deleteNoticeById(noticeId);

    }

    //공지사항 파일 다운
    @Transactional
    public FileSystemResource downloadNoticeFile(NoticeFileRequest request) {
        try {
            String fileName = request.getFileName();
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

    //공지사항 파일 가져오기
    public List<String> getNoticeFile(NoticeGetFileRequest request){

        // 공지사항 파일 가져오기 로직을 활용하여 파일 목록 가져오기
        List<String> allFiles = getNoticeFiles();

        // noticeId가  파일 필터링
        List<String> filteredFiles = allFiles.stream()
                .filter(fileName -> fileName.contains("_" + request.getNoticeId() + "_"))
                .collect(Collectors.toList());

        return filteredFiles;
    }


    //공지사항 파일 업로드
    @Transactional
    public String uploadNoticeFile(NoticeFileRequest request) {
        try {
            // 저장할 디렉토리 생성
            Files.createDirectories(Path.of(fileDirectory));

            // 파일 경로에 noticeId를 포함시킴
            String fileName = generateNoticeFileName(request);
            Path filePath = Path.of(fileDirectory, fileName);

            // 파일 저장
            Files.copy(request.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    // 공지사항 파일 삭제
    @Transactional
    public boolean deleteNoticeFile(NoticeFileRequest request) {
        try {
            // 파일명 생성
            String fileName = generateNoticeFileName(request);

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

    //공지사항 댓글 보기
    @Transactional
    public List<CommentRes> getNoticeComments(UUID noticeId){
        List<Comments> commentsList = commentRepository.findByNoticeId(noticeId).get();
        List<CommentRes> commentResList = new ArrayList<>();

        for(Comments comments : commentsList) {
            CommentRes commentRes = new CommentRes(comments);
            commentResList.add(commentRes);
        }
        return commentResList;
    }

    //공지사항 댓글 작성
    @Transactional
    public CommentRes writeNoticeComments(CommentSaveRequest commentSaveRequest){
        Comments save = commentRepository.save(commentSaveRequest.toEntity());
        CommentRes commentRes = new CommentRes(save);
        return commentRes;
    }

    //공지사항 댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @Transactional
    public void deleteNoticeComments(CommentDeleteRequest commentDeleteRequest){
        changeNullNoticeComment(commentDeleteRequest);

    }

    //공지사항 댓글 수정
    @Transactional
    public void uploadNoticeComments(CommentUpdateRequest commentUpdateRequest){
        changeNoticeComment(commentUpdateRequest);
    }

    //공지 사항 대댓글 보기
    @Transactional
    public List<ReplyCommentRes> getNoticeReplyComments(Long commentId){
        List<ReplyComments> byCommentId = replyCommentRepository.findByCommentId(commentId);
        List<ReplyCommentRes> replyCommentList = new ArrayList<>();

        for(ReplyComments replyComment : byCommentId) {
            ReplyCommentRes replyCommentRes = new ReplyCommentRes(replyComment);
            replyCommentList.add(replyCommentRes);
        }

        return replyCommentList;
    }

    //공지 사항 대댓글 작성
    @Transactional
    public ReplyCommentRes writeNoticeReplyComments(ReplyCommentSaveRequest replyCommentSaveRequest){
        ReplyComments save = replyCommentRepository.save(replyCommentSaveRequest.toEntity());
        ReplyCommentRes replyCommentRes = new ReplyCommentRes(save);
        return replyCommentRes;
    }

    //공지사항 대댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @Transactional
    public String deleteNoticeReplyComments(ReplyCommentDeleteRequest replyCommentDeleteRequest){
        changeNullNoticeReplyComment(replyCommentDeleteRequest);
        return "Success Delete!";
    }

    //공지사항 대댓글 수정
    @Transactional
    public void uploadNoticeReplyComments(ReplyCommentUpdateRequest replyCommentUpdateRequest){
        changeNoticeReplyComment(replyCommentUpdateRequest);
    }


    @Transactional
    public void changeNoticeComment(CommentUpdateRequest commentUpdateRequest){
        try {
            Comments comments = commentRepository.findByUserEmail(commentUpdateRequest.getCommentId(), commentUpdateRequest.getUserEmail()).orElseThrow(()-> new NotFoundException("수정할 댓글이 없습니다.") );
            commentRepository.save(commentUpdateRequest.toEntity());
        }catch (Exception e) {
            throw new MethodException("댓글 수정 실패");
        }
    }


    @Transactional
    public void changeNullNoticeComment(CommentDeleteRequest commentDeleteRequest){
        try {
            commentRepository.findByUserEmail(commentDeleteRequest.getCommentId(), commentDeleteRequest.getUserEmail()).orElseThrow(() -> new NotFoundException("삭제할 댓글이 없습니다."));
            commentRepository.save(commentDeleteRequest.toEntity());
        }catch (Exception e) {
            throw new MethodException("댓글 삭제 실패");
        }
    }

    @Transactional
    public void changeNoticeReplyComment(ReplyCommentUpdateRequest replyCommentUpdateRequest){
        try {
            replyCommentRepository.findByUserEmail(replyCommentUpdateRequest.getReplyId(), replyCommentUpdateRequest.getUserEmail()).orElseThrow(() -> new NotFoundException("수정할 댓글이 없습니다."));
            replyCommentRepository.save(replyCommentUpdateRequest.toEntity());
        }catch (Exception e) {
            throw new MethodException("대댓글 수정 실패");
        }
    }


    @Transactional
    public void changeNullNoticeReplyComment(ReplyCommentDeleteRequest replyCommentDeleteRequest){
        try {

            replyCommentRepository.findByUserEmail(replyCommentDeleteRequest.getReplyCommentId(), replyCommentDeleteRequest.getUserEmail()).orElseThrow(() -> new NotFoundException("삭제할 댓글이 없습니다."));
            replyCommentRepository.save(replyCommentDeleteRequest.toEntity());
        }
        catch (Exception e) {
            throw new MethodException("대댓글 삭제 실패");
        }
    }


    private String generateNoticeFileName(NoticeFileRequest request) {
        // 파일명 규칙: noticeId_originalFilename
        return String.format("%02d_%s",
                request.getNoticeId(),
                request.getFileName());
    }

    private List<String> getNoticeFiles(){
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

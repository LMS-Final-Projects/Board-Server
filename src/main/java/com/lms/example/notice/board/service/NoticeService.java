package com.lms.example.notice.board.service;

import com.lms.example.global.exception.ClientException;
import com.lms.example.global.exception.MethodException;
import com.lms.example.global.exception.NotFoundException;
import com.lms.example.notice.board.entity.*;
import com.lms.example.notice.board.dto.request.*;
import com.lms.example.notice.board.dto.response.CommentRes;
import com.lms.example.notice.board.dto.response.NoticeRes;
import com.lms.example.notice.board.dto.response.ReplyCommentRes;
import com.lms.example.notice.board.repository.*;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository adminBoardRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;
    private final MemberRepository memberRepository;
    private final NoticeFilesRepository noticeFilesRepository;

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
    public NoticeRes getNotice(UUID id){
        Notice notice = adminBoardRepository.findById(id).orElseThrow(() -> new NotFoundException("공지사항이 없습니다."));

        NoticeRes noticeRes = new NoticeRes(notice);
        return  noticeRes;
    }

    //공지사항 생성
    @Transactional
    public NoticeRes writeNotice(NoticeCreateRequest noticeCreateRequest){
        try {
            Member member = memberRepository.findById(noticeCreateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));
            List<NoticeFiles> firstFileUrls = new ArrayList<>();

            Notice save = adminBoardRepository.save(
                    Notice
                            .builder()
                            .title(noticeCreateRequest.getTitle())
                            .createAt(LocalDateTime.now())
                            .content(noticeCreateRequest.getContent())
                            .noticeFiles(firstFileUrls)
                            .member(member)
                            .build()
            );
            NoticeRes noticeRes = new NoticeRes(save);
            return noticeRes;
        }catch (Exception e) {
            throw new MethodException("");
        }
    }

    //공지사항 수정
    @Transactional
    public NoticeRes updateNotice(NoticeUpdateRequest noticeUpdateRequest) {
        Member member = memberRepository.findById(noticeUpdateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));
        adminBoardRepository.findNoticeByMemberId(noticeUpdateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("공지사항이 없습니다."));
        Notice save = adminBoardRepository.save(Notice.builder()
                .noticeId(UUID.fromString(noticeUpdateRequest.getNoticeId()))
                .title(noticeUpdateRequest.getTitle())
                .updateAt(LocalDateTime.now())
                .member(member)
                .build()
        );
        NoticeRes dto = new NoticeRes(save);
        return dto;
    }

    //공지사항 삭제
    @Transactional
    public void deleteNotice(NoticeDeleteRequest noticeDeleteRequest){
        List<Notice> noticeByNoticeIds = adminBoardRepository.findNoticeByNoticeIds(noticeDeleteRequest.getNoticeIds());
        if(noticeByNoticeIds.size()==0){
            throw new NotFoundException("공지사항이 없습니다.");
        }
        adminBoardRepository.deleteNoticeByNoticeIds(noticeDeleteRequest.getNoticeIds());

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

            //기존 경로 가져오기
            Notice notice = adminBoardRepository.findById(UUID.fromString(request.getNoticeId())).orElseThrow(() -> new NotFoundException("공지사항이 없습니다."));

            //파일경로를 리스트로 저장
            List<NoticeFiles> noticeFiles =  new ArrayList<>();

            // 새로운 파일 URL 추가
            NoticeFiles newNoticeFile = NoticeFiles.builder()
                    .fileUrl(filePath.toString()) // 이 부분은 파일 경로 대신에 파일 URL로 변경
                    .notice(notice)
                    .build();
            noticeFiles.add(newNoticeFile);

            //파일 경로를 기존 공지사항에 저장

            notice.setNoticeFiles(noticeFiles);
            adminBoardRepository.save(notice);

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

    //댓글 보기
    @Transactional
    public List<CommentRes> getComments(String id){
        List<Comments> commentsList = commentRepository.findById(UUID.fromString(id)).get();
        List<CommentRes> commentResList = new ArrayList<>();

        for(Comments comments : commentsList) {
            CommentRes commentRes = new CommentRes(comments);
            commentResList.add(commentRes);
        }
        return commentResList;
    }

    //공지사항 댓글 작성
    @Transactional
    public CommentRes writeComments(CommentSaveRequest commentSaveRequest){
        Member member = memberRepository.findById(commentSaveRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));
        Comments save = commentRepository.save(Comments
                .builder()
                .comments(commentSaveRequest.getComment())
                .boardId(commentSaveRequest.getBoardId())
                .createAt(LocalDateTime.now())
                .member(member)
                .build());
        CommentRes commentRes = new CommentRes(save);
        return commentRes;
    }

    //공지사항 댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @Transactional
    public void deleteComments(CommentDeleteRequest commentDeleteRequest){
        changeNullNoticeComment(commentDeleteRequest);

    }

    //공지사항 댓글 수정
    @Transactional
    public void uploadComments(CommentUpdateRequest commentUpdateRequest){
        changeNoticeComment(commentUpdateRequest);
    }

    //공지 사항 대댓글 보기
    @Transactional
    public List<ReplyCommentRes> getNoticeReplyComments(String boardId){
        List<ReplyComments> byCommentId = replyCommentRepository.findByBoardId(UUID.fromString(boardId));
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
        Member member = memberRepository.findById(replyCommentSaveRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));

        ReplyComments save = replyCommentRepository.save(
                ReplyComments
                .builder()
                .commentId(replyCommentSaveRequest.getCommentId())
                .comments(replyCommentSaveRequest.getComment())
                .createAt(LocalDateTime.now())
                        .boardId(UUID.fromString(replyCommentSaveRequest.getBoardId()))
                        .member(member)
                .build());
        ReplyCommentRes replyCommentRes = new ReplyCommentRes(save);
        return replyCommentRes;
    }

    //공지사항 대댓글 삭제 : 실제 삭제하는게 아니라 comment 값을 "삭제된 댓글입니다."로 만듦.
    @Transactional
    public void deleteNoticeReplyComments(ReplyCommentDeleteRequest replyCommentDeleteRequest){
        changeNullNoticeReplyComment(replyCommentDeleteRequest);
    }

    //공지사항 대댓글 수정
    @Transactional
    public void uploadNoticeReplyComments(ReplyCommentUpdateRequest replyCommentUpdateRequest){
        changeNoticeReplyComment(replyCommentUpdateRequest);
    }


    @Transactional
    public void changeNoticeComment(CommentUpdateRequest commentUpdateRequest){
        try {
            Member member = memberRepository.findById(commentUpdateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));

            Comments comments = commentRepository.findByMemberId(commentUpdateRequest.getCommentId(), commentUpdateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("수정할 댓글이 없습니다."));
            commentRepository.save(Comments
                    .builder()
                    .id(comments.getId())
                    .comments(commentUpdateRequest.getComment())
                    .boardId(commentUpdateRequest.getBoardId())
                    .updateAt(LocalDateTime.now())
                    .createAt(comments.getCreateAt())
                    .member(member)
                    .build());
        }catch (Exception e) {
            throw new MethodException("댓글 수정 실패");
        }
    }


    @Transactional
    public void changeNullNoticeComment(CommentDeleteRequest commentDeleteRequest){
        try {
            Member member = memberRepository.findById(commentDeleteRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));

            Comments comments = commentRepository.findByMemberId(commentDeleteRequest.getCommentId(), commentDeleteRequest.getMemberId()).orElseThrow(() -> new NotFoundException("삭제할 댓글이 없습니다."));
            commentRepository.save(Comments
                    .builder()
                    .id(comments.getId())
                    .comments("삭제된 댓글입니다.")
                    .updateAt(LocalDateTime.now())
                    .member(member)
                    .build()
            );
        }catch (Exception e) {
            throw new MethodException("댓글 삭제 실패");
        }
    }

    @Transactional
    public void changeNoticeReplyComment(ReplyCommentUpdateRequest replyCommentUpdateRequest){
        try {
            Member member = memberRepository.findById(replyCommentUpdateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));

            replyCommentRepository.findByMemberId(replyCommentUpdateRequest.getReplyId(), replyCommentUpdateRequest.getMemberId()).orElseThrow(() -> new NotFoundException("수정할 댓글이 없습니다."));

            replyCommentRepository.save(ReplyComments
                    .builder()
                    .comments("삭제된 댓글 입니다.")
                    .member(member)
                    .updateAt(LocalDateTime.now())
                    .build());
        }catch (Exception e) {
            throw new MethodException("대댓글 수정 실패");
        }
    }


    @Transactional
    public void changeNullNoticeReplyComment(ReplyCommentDeleteRequest replyCommentDeleteRequest){
        try {
            Member member = memberRepository.findById(replyCommentDeleteRequest.getMemberId()).orElseThrow(() -> new NotFoundException("유저 정보가 없습니다."));

            ReplyComments replyComments = replyCommentRepository.findByMemberId(replyCommentDeleteRequest.getReplyCommentId(), replyCommentDeleteRequest.getMemberId()).orElseThrow(() -> new NotFoundException("삭제할 댓글이 없습니다."));
            replyCommentRepository.save(
                    ReplyComments
                            .builder()
                            .id(replyComments.getId())
                            .comments("삭제된 댓글입니다.")
                            .updateAt(LocalDateTime.now())
                            .member(member)
                            .createAt(replyComments.getCreateAt())
                            .build()
            );
        }
        catch (Exception e) {
            throw new MethodException("대댓글 삭제 실패");
        }
    }


    private String generateNoticeFileName(NoticeFileRequest request) {
        // 파일명 규칙: noticeId_originalFilename
        // noticeId를 숫자로 변환, 지금 현재 UUID는 스트링으로 포맷으로 변환이 안된다고 합니다.
//        UUID uuid = UUID.fromString(request.getNoticeId());

        return String.format("%02d_%s",
//                uuid,
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

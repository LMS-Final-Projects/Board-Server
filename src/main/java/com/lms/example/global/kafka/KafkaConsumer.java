package com.lms.example.global.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.example.notice.board.dto.request.ClassCreateRequest;
import com.lms.example.notice.board.entity.Member;
import com.lms.example.notice.board.repository.MemberRepository;
import com.lms.example.notice.board.service.BoardService;
import com.lms.example.notice.lecture.entity.Lecture;
import com.lms.example.notice.lecture.entity.Semester;
import com.lms.example.notice.lecture.repostiory.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;
    private final BoardService boardService;

    @KafkaListener(topics = "member", groupId = "board_1")
    public void listener(String kafkaMessage) {
        Map<Object, Object> map;
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
            String action = (String) map.get("kafkaAction");
            if (action.equals(KafkaAction.CREATE.name())) {
                Member save = Member.builder()
                        .id((String) map.get("id"))
                        .name((String) map.get("name"))
                        .role((String) map.get("role"))
                        .email((String) map.get("email"))
                        .build();
                memberRepository.save(save);
            }
            System.out.println(map);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    @KafkaListener(topics = "lecture", groupId = "board_1")
    public void lectureListener(String kafkaMessage) {
        Map<Object, Object> map;
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
            String action = (String) map.get("kafkaAction");
            if (action.equals(KafkaAction.CREATE.name())) {
                Object dayOfWeek = map.get("dayOfWeek");
                DayOfWeek dayOfWeekValue = null;

                if (dayOfWeek instanceof String) {
                    String dayOfWeekString = (String) dayOfWeek;
                    dayOfWeekValue = DayOfWeek.valueOf(dayOfWeekString);
                }

                Object semester = map.get("semester");
                Semester semesterValue = null;
                if (semester instanceof String) {
                    String semesterString = (String) semester;
                    semesterValue = Semester.valueOf(semesterString);
                }

                Lecture build = Lecture.builder()
                        .memberId((String) map.get("memberId"))
                        .lectureId((Integer) map.get("lectureId"))
                        .lectureName((String) map.get("lectureName"))
                        .professorName((String) map.get("professorName"))
                        .score((Integer) map.get("score"))
                        .startTime((Integer)map.get("startTime"))
                        .classTimes((List<Integer>) map.get("classTimes"))
                        .maximumNumber((Integer)map.get("maximumNumber"))
                        .year((Integer)map.get("year"))
                        .dayOfWeek(dayOfWeekValue)
                        .semester(semesterValue)
                        .contents((String) map.get("contents"))
                        .build();
                lectureRepository.save(build);
                System.out.println("과목 저장 성공");
                ClassCreateRequest build1 = ClassCreateRequest.builder()
                        .lectureId(build.getLectureId())
                        .memberId(build.getMemberId())
                        .build();
                boardService.writeClass(build1);
                System.out.println("게시판 생성 성공");

                System.out.println(map);
            }
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }
}


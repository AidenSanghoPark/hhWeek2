package com.Week2.application.service;

import com.Week2.domain.model.Lecture;
import com.Week2.domain.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 모든 강의 조회
    @Test
    void getLectureList_ReturnsAllLectures() {
        List<Lecture> expectedLectures = Arrays.asList(new Lecture(), new Lecture());
        when(lectureRepository.findAll()).thenReturn(expectedLectures);

        List<Lecture> actualLectures = lectureService.getLectureList();

        assertEquals(expectedLectures, actualLectures);
        verify(lectureRepository, times(1)).findAll();
    }

    // id로 강의 조회
    @Test
    void getLectureById_ExistingLecture_ReturnsLecture() {
        Long lectureId = 1L;
        Lecture expectedLecture = new Lecture();
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(expectedLecture));

        Optional<Lecture> actualLecture = lectureService.getLectureById(lectureId);

        assertTrue(actualLecture.isPresent());
        assertEquals(expectedLecture, actualLecture.get());
        verify(lectureRepository, times(1)).findById(lectureId);
    }

    // 새로운 강의 생성
    @Test
    void createLecture_SavesLecture() {
        Lecture lecture = new Lecture();
        lecture.setTitle("테스트 강의");
        lecture.setDescription("이것은 테스트 강의입니다.");
        lecture.setInstructor("테스트 강사");
        lecture.setMax_Participants(30);
        lecture.setStatus("ACTIVE");

        // ArgumentCaptor를 사용하여 저장된 강의 객체를 캡처
        ArgumentCaptor<Lecture> lectureCaptor = ArgumentCaptor.forClass(Lecture.class);

        // 서비스 메소드 호출
        lectureService.createLecture(lecture);

        // lectureRepository.save() 메소드가 한 번 호출되었는지 확인하고, 저장된 객체를 캡처
        verify(lectureRepository, times(1)).save(lectureCaptor.capture());

        // 캡처된 강의 객체 가져오기
        Lecture savedLecture = lectureCaptor.getValue();

        // 저장된 강의 정보가 올바른지 검증
        assertEquals("테스트 강의", savedLecture.getTitle());
        assertEquals("이것은 테스트 강의입니다.", savedLecture.getDescription());
        assertEquals("테스트 강사", savedLecture.getInstructor());
        assertEquals(30, savedLecture.getMax_Participants());
        assertEquals("ACTIVE", savedLecture.getStatus());
    }

    @Test
    void deactivateLecture_ExistingLecture_DeactivatesLecture() {
        Long lectureId = 1L;
        Lecture lecture = new Lecture();
        lecture.setIdx(lectureId);
        lecture.setStatus("ACTIVE");

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(lecture));

        lectureService.deactivateLecture(lectureId);

        assertEquals("INACTIVE", lecture.getStatus());
        verify(lectureRepository, times(1)).update(lecture);
    }

    @Test
    void deactivateLecture_NonExistingLecture_ThrowsException() {
        Long lectureId = 1L;
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> lectureService.deactivateLecture(lectureId));

        verify(lectureRepository, never()).update(any(Lecture.class));
    }
}
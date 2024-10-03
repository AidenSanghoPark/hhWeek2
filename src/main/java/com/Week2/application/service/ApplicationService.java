package com.Week2.application.service;

import com.Week2.domain.model.Application;
import com.Week2.domain.model.Lecture;
import com.Week2.domain.repository.ApplicationRepository;
import com.Week2.domain.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LectureRepository lectureRepository;

    public void applyForLecture(Long lectureId, Long userId) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));

        //현재 수강인원은 참여수용인원보다 작아야한다
        if (lecture.getCurrent_Participants() < lecture.getMax_Participants()) {
            Application application = new Application();
            application.setLecture_Id(lectureId);
            application.setUser_Id(userId);
            application.setApplied_At(LocalDate.from(LocalDateTime.now()).atStartOfDay());

            applicationRepository.save(application);
            updateCurrentParticipants(lectureId);   //콜백 실행으로 강의테이블의 현재 인원도 업데이트
        } else {
            throw new IllegalArgumentException("Lecture is full");
        }
    }

    // Application 저장 메서드 추가
    // 같은 강의 같은 아이디 등록 안되게 로직 필요
    // 동시성 추가 로직 필요
    public void save(Application application) {
        applicationRepository.save(application);
    }

    // 모든 Application 조회 메서드 추가
    public List<Application> findAllApplications() {
        return applicationRepository.findAllApplications();
    }

    private void updateCurrentParticipants(Long lectureId) {
        // 수강관리 테이블에서 해당 강의 아이디로 등록된 수강자 count 에 수집
        long count = applicationRepository.findAllApplications().stream()
                .filter(a -> a.getLecture_Id().equals(lectureId))
                .count();

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));

        // 해당강의 테이블에 강의 관리테이블의 count 로 현재인원업데이트
        lecture.setCurrent_Participants((int) count);
        
        // 최대인원되면 비활성화
        if (count == lecture.getMax_Participants()) {
            lecture.setStatus("INACTIVE");
        }

        lectureRepository.update(lecture);
    }
}

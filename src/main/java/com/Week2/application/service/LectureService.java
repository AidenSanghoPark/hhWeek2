package com.Week2.application.service;

import com.Week2.domain.model.Lecture;
import com.Week2.domain.repository.LectureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    //강의 전체 조회
    public List<Lecture> getLectureList() {
        return lectureRepository.findAll();
    }

    //아이디로 강의 조회
    public Optional<Lecture> getLectureById(Long id) {
        return lectureRepository.findById(id);
    }

    //Lecture 넣어서 강의 생성
    @Transactional
    public void createLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }


    @Transactional
    public void deactivateLecture(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));

        lecture.setStatus("INACTIVE");
        lectureRepository.update(lecture);
    }

}

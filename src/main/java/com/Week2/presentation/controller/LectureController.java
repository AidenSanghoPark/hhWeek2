package com.Week2.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Week2.domain.model.Lecture;
import com.Week2.application.service.LectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/lectures")
public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // 강의 생성
    @Operation(summary = "강의 생성", description = "새로운 강의를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @PostMapping
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) {
        lectureService.createLecture(lecture);
        return ResponseEntity.ok(lecture);
    }

    // 모든 강의 조회
    @Operation(summary = "모든 강의 조회", description = "등록된 모든 강의를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의 목록을 성공적으로 조회했습니다.")
    })
    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLectures() {
        List<Lecture> lectures = lectureService.getLectureList();
        return ResponseEntity.ok(lectures);
    }

    // 특정 강의 조회
    @Operation(summary = "특정 강의 조회", description = "ID로 특정 강의를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의를 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 강의를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(
            @Parameter(description = "조회할 강의의 ID", required = true) @PathVariable Long id) {
        return lectureService.getLectureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 강의 아이디로 삭제
//    @Operation(summary = "강의 삭제", description = "ID로 특정 강의를 삭제합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "강의가 성공적으로 삭제되었습니다."),
//            @ApiResponse(responseCode = "404", description = "해당 ID의 강의를 찾을 수 없습니다.")
//    })
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteLecture(@PathVariable(name = "id") Long id) {
//        Optional<Lecture> lecture = lectureService.getLectureById(id);
//        if (lecture.isPresent()) {
//            lectureService.deleteLecture(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @Operation(summary = "강의 비활성화", description = "ID로 특정 강의를 비활성화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "강의가 성공적으로 비활성화되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 강의를 찾을 수 없습니다.")
    })
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateLecture(@PathVariable(name = "id") Long id) {
        Optional<Lecture> lecture = lectureService.getLectureById(id);
        if (lecture.isPresent()) {
            lectureService.deactivateLecture(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.Week2.presentation.controller;

import com.Week2.application.service.ApplicationService;
import com.Week2.domain.model.Application;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Application 생성
//    @Operation(summary = "신청서 생성", description = "새로운 신청서를 생성합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "신청서가 성공적으로 생성되었습니다."),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
//    })
//    @PostMapping
//    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
//        applicationService.save(application);
//        return ResponseEntity.ok(application);
//    }

    @Operation(summary = "신청서 생성 및 강의정보 업데이트", description = "새로운 신청서를 생성하고 강의 정보 수강 인원을 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신청서가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @PostMapping
    public ResponseEntity<String> applyForLecture(@RequestBody Application application) {
        applicationService.applyForLecture(application.getLecture_Id(), application.getUser_Id());
        return ResponseEntity.ok("Application successful");
    }

    // 모든 Application 조회
    @Operation(summary = "모든 신청서 조회", description = "등록된 모든 신청서를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신청서 목록을 성공적으로 조회했습니다.")
    })
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.findAllApplications();
        return ResponseEntity.ok(applications);
    }
}

package com.Week2.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LECTURE")
@Schema(description = "강의 정보")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Schema(description = "강의 ID", example = "1")
    private Long idx;

    @Schema(description = "강의 제목", example = "Java 기초")
    private String title;

    @Schema(description = "강의 설명", example = "Java 기초 강의입니다.")
    private String description;

    @Schema(description = "강의 강사", example = "홍길동")
    private String instructor;

    @Schema(description = "강의 시작 날짜")
    private LocalDate start_Date;

    @Schema(description = "강의 종료 날짜")
    private LocalDate end_Date;

    @Schema(description = "최대 참가자 수", example = "30")
    private int max_Participants;

    @Schema(description = "현재 참가자 수", example = "10")
    private int current_Participants;

    @Column(nullable = false)
    @Schema(description = "강의 상태", example = "ACTIVE")
    private String status = "ACTIVE";
}

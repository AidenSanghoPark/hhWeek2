package com.Week2.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LECTURE_APPLY")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "lecture_Id")
    private Long lecture_Id;

    @Column(name = "user_Id")
    private Long user_Id;

    @Column(name = "applied_At")
    private LocalDateTime applied_At;
}

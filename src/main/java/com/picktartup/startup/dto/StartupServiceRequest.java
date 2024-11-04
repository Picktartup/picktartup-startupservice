package com.picktartup.startup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StartupServiceRequest {
    private String name; // 스타트업 이름
    private String category; // 카테고리
    private LocalDateTime contractStartDate; // 시작 기한
    private LocalDateTime contractTargetDeadline; // 마감 기한
    private int progress; // 진행도 (백분율)
    private int currentCoin; // 모인 코인
    private int goalCoin; // 목표 코인
}

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
    private String name;
    private String category;
    private LocalDateTime contractStartDate;
    private LocalDateTime contractTargetDeadline;
    private String progress;
    private int currentCoin;
    private int goalCoin;
    private double fundingProgress; //목표 대비 모금 진행 상황 추가
}

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
    private int progress;
    private int currentCoin;
    private int goalCoin;
}

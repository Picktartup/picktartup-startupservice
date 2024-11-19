package com.picktartup.startup.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartupServiceRequest {

    private Long startupId;
    private String name;
    private String description;
    private String category;
    private LocalDateTime investmentStartDate;
    private LocalDateTime investmentTargetDeadline;
    private String progress;
    private int currentCoin;
    private int goalCoin;
    private int fundingProgress;
    private List<SSIServiceRequest> ssiList;

    private String investmentStatus;
    private String investmentRound;

    private String address;
    private String ceoName;
    private String page;
    private String establishmentDate;
}

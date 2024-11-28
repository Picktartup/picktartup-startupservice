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
    private Double currentCoin;
    private Integer goalCoin;
    private int fundingProgress;
    private List<SSIServiceRequest> ssiList;

    private String investmentStatus;

    private String address;
    private String ceoName;
    private String page;
    private String establishmentDate;

    private String logoUrl;
    private String current_round;

    private String registration_num;
    private Integer contract_period;
    private String signature;
    private Long ceo_user_id;
    private String industry_type;
    private Integer campaign_id;
    private Double expected_roi;
    private Double roi;

}

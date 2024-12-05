package com.picktartup.startup.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AnnualMetricsResponse {
    private Long annualId;
    private Integer year;
    private Long annualRevenue;
    private Long operatingProfit;
    private Long totalAsset;
    private Long netProfit;
    private LocalDateTime createdAt;
    private String dataSource;

    private String investmentRound;
    // Optional: 연관된 Startup 정보가 필요한 경우
    private Long startupId;
}
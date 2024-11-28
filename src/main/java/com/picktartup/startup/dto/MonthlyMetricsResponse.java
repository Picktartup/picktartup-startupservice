package com.picktartup.startup.dto;

import com.picktartup.startup.entity.Startup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MonthlyMetricsResponse {
    private Long monthlyId;
    private LocalDateTime metricDate;
    private Integer mau;
    private Integer employeeCount;
    private LocalDateTime createdAt;
    private String dataSource;

    private Long startupId;
}

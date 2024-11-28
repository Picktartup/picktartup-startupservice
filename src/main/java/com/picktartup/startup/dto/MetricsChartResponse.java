package com.picktartup.startup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricsChartResponse {
    private String date;  // "YYYY" or "YYYY-MM" 형식
    private Long revenue; // annual or monthly revenue
    private Long operatingProfit;
    private Long netProfit;
    private Integer mau;     // monthly only
    private Integer employeeCount; // monthly only
}
package com.picktartup.startup.service;

import com.picktartup.startup.dto.AnnualMetricsResponse;
import com.picktartup.startup.dto.MetricsChartResponse;
import com.picktartup.startup.dto.MonthlyMetricsResponse;
import com.picktartup.startup.dto.StartupElasticsearch;
import com.picktartup.startup.dto.StartupServiceRequest;

import java.util.List;

public interface StartupService {
    List<StartupServiceRequest> getTop6StartupsByProgress();
    List<StartupElasticsearch> searchStartupsByKeyword(String keyword);
    List<StartupElasticsearch> findAllStartupsInElasticsearch();

    StartupServiceRequest getStartupDetailsFromPostgresql(Long startupId);
    StartupServiceRequest getStartupDetailsFromElasticsearch(Long startupId);

    List<StartupServiceRequest> getAllStartupsWithLogoUrl();
    List<AnnualMetricsResponse> getAnnualMetrics(Long startupId);
    List<MonthlyMetricsResponse> getMonthlyMetrics(Long startupId);
    List<MetricsChartResponse> getMetricsForChart(Long startupId, String period);

}

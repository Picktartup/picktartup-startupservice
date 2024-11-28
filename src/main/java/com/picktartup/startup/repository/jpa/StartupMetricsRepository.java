package com.picktartup.startup.repository.jpa;

import com.picktartup.startup.entity.StartupAnnualMetrics;
import com.picktartup.startup.entity.StartupMonthlyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StartupMetricsRepository extends JpaRepository<StartupAnnualMetrics, Long> {
    List<StartupAnnualMetrics> findByStartup_StartupIdOrderByYear(Long startupId);

    @Query("SELECT sm FROM StartupMonthlyMetrics sm WHERE sm.startup.id = :startupId ORDER BY sm.metricDate")
    List<StartupMonthlyMetrics> findMonthlyByStartupIdOrderByMetricDate(@Param("startupId") Long startupId);

    @Query("SELECT sm FROM StartupMonthlyMetrics sm WHERE sm.startup.id = :startupId AND sm.metricDate BETWEEN :startDate AND :endDate ORDER BY sm.metricDate")
    List<StartupMonthlyMetrics> findMonthlyByStartup_StartupIdAndDateRange(
            @Param("startupId") Long startupId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
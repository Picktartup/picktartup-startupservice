package com.picktartup.startup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "startup_monthlymetrics")
@Entity
public class StartupMonthlyMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthly_metrics_seq")
    @SequenceGenerator(
            name = "monthly_metrics_seq",
            sequenceName = "monthly_metrics_seq",
            allocationSize = 1
    )
    @Column(name = "monthly_id")
    private Long monthlyId;       // 변수명도 monthlyId로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id", nullable = false)
    private Startup startup;

    @Column(name = "metric_date")
    private LocalDateTime metricDate;

    @Column (name = "mau")
    private Integer mau;

    @Column (name = "employee_count")
    private Integer employeeCount;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    @Column (name = "data_source")
    private String dataSource;
}

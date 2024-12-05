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
@Table(name = "startup_annualmetrics")
@Entity
public class StartupAnnualMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "annual_metrics_seq")
    @SequenceGenerator(
            name = "annual_metrics_seq",
            sequenceName = "annual_metrics_seq",
            allocationSize = 1
    )
    @Column(name = "annual_id")
    private Long annualId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id", nullable = false)
    private Startup startup;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "annual_revenue")
    private Long annualRevenue;

    @Column (name= "operating_profit")
    private Long operatingProfit;

    @Column (name = "total_asset")
    private Long totalAsset;

    @Column (name = "net_profit")
    private Long netProfit;


    @Column (name = "created_at")
    private LocalDateTime createdAt;

    @Column (name = "data_source")
    private String dataSource;

    @Column(name = "investment_round", nullable = false, length = 20)
    private String investmentRound;
}



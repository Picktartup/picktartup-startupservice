package com.picktartup.startup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "startup")
@Entity
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "startup_seq_generator")
    @SequenceGenerator(name = "startup_seq_generator", sequenceName = "startup_seq", allocationSize = 1)
    @Column(name = "startup_id",nullable = false)
    private Long startupId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "progress", nullable = false)
    private Integer progress;

    @Column(name = "investment_start_date", nullable = false)
    private LocalDateTime investmentStartDate;

    @Column(name = "investment_target_deadline", nullable = false)
    private LocalDateTime investmentTargetDeadline;

    @Column(name = "goal_coin", nullable = false)
    private Integer goalCoin;

    @Column(name = "current_coin")
    private Double currentCoin;

    @Column(name = "funding_progress")
    private Integer fundingProgress;


    @Column(name = "wallet_id" , nullable = false)
    private Long walletId;

    @OneToOne(mappedBy = "startup" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StartupDetails startupDetails;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SSI> ssi;



    @PrePersist
    @PreUpdate
    public void calculateFundingProgress() {
        if (this.currentCoin != null && this.goalCoin != null && this.goalCoin > 0) {
            this.fundingProgress = (int) ((double) this.currentCoin / this.goalCoin * 100);
        }else {
            this.fundingProgress = 0;  // null 대신 0으로 설정
        }
    }

}

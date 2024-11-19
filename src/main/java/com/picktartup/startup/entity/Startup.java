package com.picktartup.startup.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "startup")
@Entity
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "startup_id") // 기본 키로 설정
    private Long startupId; // 유일한 ID 필드

    @OneToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    private String name;
    private String description;
    private String category;
    private String progress;
    private Double ssi;
    private LocalDateTime contractStartDate;
    private LocalDateTime contractTargetDeadline;
    private Integer goalCoin;
    private Double expectedReturn;
    private Integer currentCoin;

    @Transient
    public int getFundingProgress() {
        return (goalCoin != null && goalCoin > 0) ? (int) ((double) currentCoin / goalCoin * 100) : 0;
    }

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL)
    private Set<Contract> contracts;
}

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
    @Column(name = "startup_id")
    private Long startupId;

    @OneToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    private String name;
    private String description;
    private String category;
    private String progress;
    private LocalDateTime investmentStartDate;
    private LocalDateTime investmentTargetDeadline;
    private Integer goalCoin;
    private Double expectedReturn;
    private Integer currentCoin;
    private Integer fundingProgress;

    //투자 상태, 투자 라운드 추가
    private String investmentStatus;
    private String investmentRound;


    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SSI> ssi;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL)
    private Set<Contract> contracts;
}

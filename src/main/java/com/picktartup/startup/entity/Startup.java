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
    private Integer currentCoin;
    private Integer fundingProgress;
    private String investmentStatus;
    private String investmentRound;

    //예상 수익률, 실제 수익률, 사업장 주소, 대표 이름, 사업자 등록 번호, 계약 기간, 홈페이지, 설립일자 추가
    private String expectedROI;
    private String ROI;
    private String address;
    private String ceoName;
    private String registrationNum;
    private Integer contractPeriod;
    private String page;
    private String establishmentDate;


    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SSI> ssi;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL)
    private Set<Contract> contracts;

}

package com.picktartup.startup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "startup_details")
@Entity
public class StartupDetails {

    @Id
    @Column(name = "startup_id" ,  nullable = false)
    private Long startupId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "startup_id" , nullable = false)
    private Startup startup;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "investment_status", nullable = false, length = 10)
    private String investmentStatus;


    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "ceo_name", nullable = false, length = 10)
    private String ceoName;

    @Column(name = "registration_num", nullable = false, length = 30)
    private String registrationNum;

    @Column(name = "contract_period", nullable = false)
    private Integer contractPeriod;

    @Column(name = "page", nullable = false, length = 30)
    private String page;

    @Column(name = "establishment_date", nullable = false, length = 30)
    private String establishmentDate;

    @Column(name = "expected_roi")
    private Double expectedRoi;

    @Column(name = "roi")
    private Double roi;

    @Column(name = "signature")
    private String signature;

    @Column(name = "current_round")
    private String currentRound;


}

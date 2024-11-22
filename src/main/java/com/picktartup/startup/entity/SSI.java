package com.picktartup.startup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "ssi")
@Entity
public class SSI {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ssi_seq_generator")
    @SequenceGenerator(name = "ssi_seq_generator", sequenceName = "ssi_seq", allocationSize = 1)
    @Column(name = "ssi_id",nullable = false)
    private Long ssiId;


    @Column(name = "people_grade", nullable = false, length = 10)
    private String peopleGrade;

    @Column(name = "product_grade", nullable = false, length = 10)
    private String productGrade;

    @Column(name = "performance_grade", nullable = false, length = 10)
    private String performanceGrade;

    @Column(name = "potential_grade", nullable = false, length = 10)
    private String potentialGrade;

    @Column(name = "eval_date", nullable = false)
    private LocalDateTime evalDate;


    @Column(name = "eval_description", columnDefinition = "text")
    private String evalDescription;

    @Column(name = "startup_id", nullable = false)
    private Long startupId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id", insertable = false, updatable = false)
    private Startup startup;
}

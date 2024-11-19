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
    @Column(name = "ssi_id")
    private Long ssiId;

    // Many-to-One 관계 설정: Startup과 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id", nullable = false)
    private Startup startup;

    private String peopleGrade;
    private String productGrade;
    private String performanceGrade;
    private String potentialGrade;
    private LocalDateTime evalDate;

    @Column(columnDefinition = "text")
    private String evalDescription;

}

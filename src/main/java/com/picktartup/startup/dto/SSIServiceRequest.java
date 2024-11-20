package com.picktartup.startup.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(indexName = "ssi")
public class SSIServiceRequest {
    @Id
    private Long ssiId;

    private String peopleGrade;
    private String productGrade;
    private String performanceGrade;
    private String potentialGrade;
    private LocalDateTime evalDate;
    private String evalDescription;
}

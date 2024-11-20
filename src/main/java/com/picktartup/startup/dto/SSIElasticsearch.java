// src/main/java/com/picktartup/startup/dto/SSIElasticsearch.java
package com.picktartup.startup.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "ssi")
public class SSIElasticsearch {

    @Id
    @Field(type = FieldType.Long)
    private Long ssiId;

    @Field(name = "people_grade", type = FieldType.Keyword)
    private String peopleGrade;

    @Field(name = "product_grade", type = FieldType.Keyword)
    private String productGrade;

    @Field(name = "performance_grade", type = FieldType.Keyword)
    private String performanceGrade;

    @Field(name = "potential_grade", type = FieldType.Keyword)
    private String potentialGrade;

    @Field(name = "eval_date", type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime evalDate;

    @Field(name = "eval_description", type = FieldType.Text)
    private String evalDescription;

    private List<SSIElasticsearch> ssiList;

    @Field(name = "startup_id", type = FieldType.Long)
    private Long startupId;
}

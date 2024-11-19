package com.picktartup.startup.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(indexName = "startups")
public class ElasticsearchStartupEntity {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Text)
    private String progress;

    @Field(type = FieldType.Double)
    private Double ssi;

    @Field(name = "contract_start_date", type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime contractStartDate;

    @Field(name = "contract_target_deadline", type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime contractTargetDeadline;

    @Field(name = "goal_coin", type = FieldType.Integer)
    private Integer goalCoin;

    @Field(name = "current_coin", type = FieldType.Integer)
    private Integer currentCoin;

    @Field(name = "expected_return", type = FieldType.Double)
    private Double expectedReturn;

    @Field(name = "wallet_id", type = FieldType.Integer)
    private Integer walletId;
}

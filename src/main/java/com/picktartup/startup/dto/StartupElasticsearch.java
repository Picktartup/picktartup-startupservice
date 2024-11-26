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
@Document(indexName = "startups")
public class StartupElasticsearch {

    @Id
    @Field(type = FieldType.Long)
    private Long startupId;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String progress;

    @Field(type = FieldType.Double)
    private Double ssi;

    @Field(name = "investment_start_date", type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime investmentStartDate;

    @Field(name = "investment_target_deadline", type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime investmentTargetDeadline;

    @Field(name = "goal_coin", type = FieldType.Integer)
    private Integer goalCoin;

    @Field(name = "current_coin", type = FieldType.Integer)
    private Double currentCoin;

    @Field(name = "expected_return", type = FieldType.Double)
    private Double expectedReturn;

    @Field(name = "wallet_id", type = FieldType.Long)
    private Long walletId;

    @Field(name = "funding_progress", type = FieldType.Integer)
    private Integer fundingProgress;

    @Field(name = "investment_status",type = FieldType.Text)
    private String investmentStatus;

    @Field(name = "investment_round",type = FieldType.Text)
    private String investmentRound;

    private List<SSIElasticsearch> ssiList;

    public void calculateAndSetFundingProgress() {
        if (this.goalCoin == null || this.goalCoin == 0 || this.currentCoin == null) {
            this.fundingProgress = 0;
        } else {
            this.fundingProgress = (int) ((double) this.currentCoin / this.goalCoin * 100);
        }
    }
    @Field(name = "address", type = FieldType.Text)
    private String address;

    @Field(name = "ceo_name", type = FieldType.Text)
    private String ceoName;

    @Field(name = "page", type = FieldType.Text)
    private String page;

    @Field(name = "establishment_date", type = FieldType.Text)
    private String establishmentDate;
}

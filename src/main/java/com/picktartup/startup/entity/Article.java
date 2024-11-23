package com.picktartup.startup.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@DynamoDBTable(tableName = "articles")
@Getter
@Setter
public class Article {
    @DynamoDBHashKey(attributeName = "id")  // 테이블의 파티션 키 이름과 일치시킴
    private String id;

    @DynamoDBAttribute(attributeName = "url")
    private String url;

    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @DynamoDBAttribute(attributeName = "imageUrl")
    private String imageUrl;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "keyword-index", attributeName = "keyword")
    private String keyword;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;  // ISO 8601 형식
}

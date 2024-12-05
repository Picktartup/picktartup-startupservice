package com.picktartup.startup.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.picktartup.startup.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleDynamoDBRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public Article save(Article article) {
        dynamoDBMapper.save(article);
        return article;
    }

    public List<Article> findByKeywordOrderByCreatedAtDesc(String keyword) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(keyword));

        DynamoDBQueryExpression<Article> queryExpression = new DynamoDBQueryExpression<Article>()
                .withIndexName("keyword-index")
                .withKeyConditionExpression("keyword = :val1")
                .withExpressionAttributeValues(eav)
                .withScanIndexForward(false)
                .withConsistentRead(false);  // GSI에서는 반드시 false로 설정해야 함

        return dynamoDBMapper.query(Article.class, queryExpression);
    }

    public boolean existsByUrl(String url) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(url));

        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#url", "url");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#url = :val1")
                .withExpressionAttributeNames(expressionAttributeNames)
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);  // 여기도 false로 설정

        List<Article> result = dynamoDBMapper.scan(Article.class, scanExpression);
        return !result.isEmpty();
    }

    public Optional<Article> findFirstByKeywordOrderByCreatedAtDesc(String keyword) {
        List<Article> articles = findByKeywordOrderByCreatedAtDesc(keyword);
        return articles.isEmpty() ? Optional.empty() : Optional.of(articles.get(0));
    }
}
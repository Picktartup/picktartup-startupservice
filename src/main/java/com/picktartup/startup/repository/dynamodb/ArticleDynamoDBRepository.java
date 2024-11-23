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
                .withIndexName("keyword-index")  // 새로 생성된 GSI 이름
                .withKeyConditionExpression("keyword = :val1")
                .withExpressionAttributeValues(eav)
                .withScanIndexForward(false);  // 내림차순 정렬

        return dynamoDBMapper.query(Article.class, queryExpression);
    }

    public boolean existsByUrl(String url) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(url));

        // 예약 키워드(url)를 Attribute Name Mapping으로 대체
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#url", "url");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#url = :val1") // 예약 키워드 대신 매핑된 이름 사용
                .withExpressionAttributeNames(expressionAttributeNames) // 매핑 추가
                .withExpressionAttributeValues(eav);

        List<Article> result = dynamoDBMapper.scan(Article.class, scanExpression);
        return !result.isEmpty();
    }

    public Optional<Article> findFirstByKeywordOrderByCreatedAtDesc(String keyword) {
        List<Article> articles = findByKeywordOrderByCreatedAtDesc(keyword);
        return articles.isEmpty() ? Optional.empty() : Optional.of(articles.get(0));
    }
}

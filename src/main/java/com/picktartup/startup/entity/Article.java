package com.picktartup.startup.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
@Data
@Document(collection = "articles")
public class Article {
    @Id
    private String id;
    private String url;          // URL만 입력
    private String title;        // Spring Boot가 자동으로 채움
    private String imageUrl;     // Spring Boot가 자동으로 채움
    private String keyword;      // 사용자가 입력한 키워드
    private Date createdAt;
}

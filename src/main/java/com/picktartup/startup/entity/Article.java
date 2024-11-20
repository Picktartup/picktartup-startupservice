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
    private String url;
    private String title;
    private String imageUrl;
    private String keyword;
    private Date createdAt;
}

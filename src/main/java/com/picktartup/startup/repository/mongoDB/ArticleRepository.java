package com.picktartup.startup.repository.mongoDB;

import com.picktartup.startup.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {
    // MongoDB에서 keyword로 검색
    List<Article> findByKeyword(String keyword);
}

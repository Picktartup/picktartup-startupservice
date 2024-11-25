package com.picktartup.startup.repository.mongoDB;

import com.picktartup.startup.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String> {
    List<Article> findByKeywordOrderByCreatedAtDesc(String keyword);
    boolean existsByUrl(String url);
    Optional<Article> findFirstByKeywordOrderByCreatedAtDesc(String keyword);
}

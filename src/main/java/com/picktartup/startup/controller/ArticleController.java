package com.picktartup.startup.controller;

import com.picktartup.startup.common.dto.ApiResponse;
import com.picktartup.startup.entity.Article;

import com.picktartup.startup.repository.mongoDB.ArticleRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
@CrossOrigin(origins = "http://localhost:3000")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("health_check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.ok("서비스가 정상 작동 중입니다."));
    }
    // URL 입력 후 MongoDB에 기사 저장
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveArticle(
            @RequestParam String url,
            @RequestParam String keyword) {
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            String imageUrl = doc.select("meta[property=og:image]").attr("content");

            Article article = new Article();
            article.setUrl(url);
            article.setTitle(title != null ? title : "제목 없음");
            article.setImageUrl(imageUrl != null ? imageUrl : "default.jpg");
            article.setKeyword(keyword);
            article.setCreatedAt(new Date());

            Article savedArticle = articleRepository.save(article);

            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "기사 저장 성공");
            response.put("data", savedArticle);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 500);
            response.put("message", "기사 저장 실패");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 키워드로 기사 조회
    @GetMapping("/{keyword}")
    public ResponseEntity<Map<String, Object>> getArticlesByKeyword(@PathVariable String keyword) {
        List<Article> articles = articleRepository.findByKeyword(keyword);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "기사 조회 성공");
        response.put("data", articles);

        return ResponseEntity.ok(response);
    }
}

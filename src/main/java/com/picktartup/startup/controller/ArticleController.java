package com.picktartup.startup.controller;

import com.picktartup.startup.common.dto.ApiResponse;
import com.picktartup.startup.entity.Article;

import com.picktartup.startup.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("health_check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("서비스가 정상 작동 중입니다.");
    }

    @GetMapping("/startup/{startupId}")
    public ResponseEntity<List<Article>> getArticlesByStartupId(@PathVariable Long startupId) {
        return ResponseEntity.ok(articleService.getNewsByStartupId(startupId));
    }

    @PostMapping("/scrape/{startupId}")
    public ResponseEntity<List<Article>> scrapeArticles(@PathVariable Long startupId) {
        return ResponseEntity.ok(articleService.scrapeAndSaveNews(startupId));
    }
}

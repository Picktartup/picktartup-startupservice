package com.picktartup.startup.service.impl;

import com.picktartup.startup.entity.Article;
import com.picktartup.startup.repository.mongoDB.ArticleRepository;
import com.picktartup.startup.service.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article saveArticle(String url, String keyword) {
        try {
            // Jsoup으로 웹 페이지 크롤링
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            String imageUrl = doc.select("meta[property=og:image]").attr("content");

            // Article 객체 생성 및 저장
            Article article = new Article();
            article.setUrl(url);
            article.setTitle(title != null ? title : "제목 없음");
            article.setImageUrl(imageUrl != null ? imageUrl : "default.jpg");
            article.setKeyword(keyword);
            article.setCreatedAt(new Date());

            return articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("기사 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @Override
    public List<Article> getArticlesByKeyword(String keyword) {
        return articleRepository.findByKeyword(keyword);
    }
}

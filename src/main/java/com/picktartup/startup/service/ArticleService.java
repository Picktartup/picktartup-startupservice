package com.picktartup.startup.service;

import com.picktartup.startup.entity.Article;

import java.util.List;

public interface ArticleService {
    Article saveArticle(String url, String keyword); // 기사 저장
    List<Article> getArticlesByKeyword(String keyword); // 키워드로 기사 조회
}

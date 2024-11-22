package com.picktartup.startup.service;

import com.picktartup.startup.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getNewsByStartupId(Long startupId);
    List<Article> scrapeAndSaveNews(Long startupId);
}

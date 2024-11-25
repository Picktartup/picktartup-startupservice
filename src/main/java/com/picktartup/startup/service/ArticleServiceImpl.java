package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupCrawling;
import com.picktartup.startup.entity.Article;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import com.picktartup.startup.repository.mongoDB.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final StartupServiceRepository startupRepository;

    @Override
    public List<Article> getNewsByStartupId(Long startupId) {
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new IllegalArgumentException("Startup not found: " + startupId));
        return articleRepository.findByKeywordOrderByCreatedAtDesc(startup.getName());
    }

    @Override
    public List<Article> scrapeAndSaveNews(Long startupId) {
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new IllegalArgumentException("Startup not found: " + startupId));

        StartupCrawling startupDTO = StartupCrawling.from(startup);
        List<Article> newArticles = new ArrayList<>();

        try {
            String url = "https://thevc.kr/" + startupDTO.getEnglishName();
            log.info("Scraping URL: {} for startup: {}", url, startupDTO.getKoreanName());

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(5000)
                    .get();

            Elements newsItems = doc.select("a.block-wrap.clickable.rounded-border");


            for (Element newsItem : newsItems) {
                String title = newsItem.select("h3.text-16.text-bold.mt-4.mb-16.text-truncate-from-2nd").text().trim();
                String articleUrl = newsItem.attr("href");

                if (!articleUrl.startsWith("http")) {
                    articleUrl = "https://thevc.kr" + articleUrl;
                }

                log.info("Found news - Title: '{}', URL: '{}'", title, articleUrl);

                if (title.isEmpty() || articleUrl.isEmpty() || articleRepository.existsByUrl(articleUrl)) {
                    continue;
                }

                try {
                    Document articleDoc = Jsoup.connect(articleUrl)
                            .userAgent("Mozilla/5.0")
                            .timeout(5000)
                            .get();

                    String imageUrl = findImageUrl(articleDoc);

                    Article article = new Article();
                    article.setUrl(articleUrl);
                    article.setTitle(title);
                    article.setImageUrl(imageUrl);
                    article.setKeyword(startupDTO.getKoreanName());
                    article.setCreatedAt(new Date());

                    Article savedArticle = articleRepository.save(article);
                    newArticles.add(savedArticle);
                    log.info("Saved new article: {}", title);

                    if (newArticles.size() >= 12) {
                        break;
                    }

                    Thread.sleep(1000);

                } catch (Exception e) {
                    log.error("Error processing article URL: " + articleUrl, e);
                }
            }



        } catch (Exception e) {
            log.error("Error scraping news for startup: {}", startupDTO.getKoreanName(), e);
            throw new RuntimeException("Failed to scrape news", e);
        }

        log.info("Successfully scraped {} articles for {}", newArticles.size(), startupDTO.getKoreanName());
        return newArticles;
    }

    private String findImageUrl(Document doc) {
        Element ogImage = doc.select("meta[property=og:image]").first();
        if (ogImage != null) {
            return ogImage.attr("content");
        }

        Elements images = doc.select("img[src*=news], img[src*=article], .article-image img");
        return !images.isEmpty() ? images.first().absUrl("src") : "";
    }
}
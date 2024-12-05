package com.picktartup.startup.service;

import com.picktartup.startup.entity.Article;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.service.ArticleService;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class ArticleCrawlingScheduler {

    private final ArticleService articleService;
    private final StartupServiceRepository startupRepository;


    @Scheduled(fixedRate = 60000)  // 테스트용 60초
    //@Scheduled(cron = "0 0 3 * * ?")  // 실제 운영 (매일 새벽 3시)
    public void scheduledCrawling() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("Starting scheduled article crawling at {}", currentTime);

        try {
            List<Startup> startups = startupRepository.findAll();
            log.info("Found {} startups to process", startups.size());

            int totalNewArticles = 0;

            for (Startup startup : startups) {
                try {
                    log.info("Processing startup: {} (ID: {})", startup.getName(), startup.getStartupId());

                    List<Article> articles = articleService.scrapeAndSaveNews(startup.getStartupId());
                    totalNewArticles += articles.size();

                    if (!articles.isEmpty()) {
                        log.info("Found {} new articles for startup: {}", articles.size(), startup.getName());
                        articles.forEach(article ->
                                log.info("New article: {} ({})", article.getTitle(), article.getUrl())
                        );
                    } else {
                        log.info("No new articles found for startup: {}", startup.getName());
                    }

                    Thread.sleep(2000); // 크롤링 간격 2초

                } catch (Exception e) {
                    log.error("Error crawling for startup: {} - {}", startup.getName(), e.getMessage());
                    continue;
                }
            }

            log.info("Crawling completed at {}. Total new articles: {}",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    totalNewArticles);

        } catch (Exception e) {
            log.error("Fatal error in crawling scheduler", e);
        }
    }
}
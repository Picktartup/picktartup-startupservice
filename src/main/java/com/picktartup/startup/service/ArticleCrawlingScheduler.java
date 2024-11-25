package com.picktartup.startup.service;

import com.picktartup.startup.entity.Article;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.service.ArticleService;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleCrawlingScheduler {

    private final ArticleService articleService;
    private final StartupServiceRepository startupRepository;

    @Scheduled(fixedRate = 60000)  // 60초 테스트용
    //@Scheduled(cron = "0 0 3 * * ?")  // 실제 운영 (매일 새벽 3시)
    public void scheduledCrawling() {
        log.info("Starting article crawling...");

        try {
            List<Startup> startups = startupRepository.findAll();

            for (Startup startup : startups) {
                try {
                    log.info("Crawling for startup: {}", startup.getName());
                    List<Article> articles = articleService.scrapeAndSaveNews(startup.getStartupId());
                    log.info("Crawled {} articles for {}", articles.size(), startup.getName());

                    Thread.sleep(2000); // 크롤링 간격 조절

                } catch (Exception e) {
                    log.error("Error crawling for startup: " + startup.getName(), e);
                    continue;
                }
            }

            log.info("Crawling completed successfully");

        } catch (Exception e) {
            log.error("Fatal error in crawling scheduler", e);
        }
    }
}
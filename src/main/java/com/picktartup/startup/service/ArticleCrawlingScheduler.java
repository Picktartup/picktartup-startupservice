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

    // 테스트할 때는 30초로 설정
    //@Scheduled(fixedRate = 30000)  // 30초마다
    @Scheduled(cron = "0 0 3 * * ?")  // 실제 운영할 때는 이걸로 변경 (매일 새벽 3시)
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
                    // 한 스타트업이 실패해도 다음으로 진행
                    continue;
                }
            }

            log.info("Crawling completed successfully");

        } catch (Exception e) {
            log.error("Fatal error in crawling scheduler", e);
        }
    }
}
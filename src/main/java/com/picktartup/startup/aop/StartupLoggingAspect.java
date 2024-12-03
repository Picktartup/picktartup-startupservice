package com.picktartup.startup.aop;

import com.picktartup.startup.dto.StartupElasticsearch;
import com.picktartup.startup.dto.StartupServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class StartupLoggingAspect {

    private void logBusinessAction(String action, Map<String, Object> additionalFields) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("log_type", "business");
        logData.put("action", action);
        logData.put("timestamp", LocalDateTime.now().toString());
        logData.putAll(additionalFields);

        log.info("{}", logData);
    }

    /**
     * 스타트업 조회 로깅
     */
    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.getStartupDetailsFromElasticsearch(..)) || " +
            "execution(* com.picktartup.startup.service.StartupServiceImpl.getStartupDetailsFromPostgresql(..))")
    public Object logStartupView(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long startupId = (Long) args[0];
        LocalDateTime viewTime = LocalDateTime.now();

        try {
            Object result = joinPoint.proceed();

            if (result instanceof StartupServiceRequest) {
                StartupServiceRequest startup = (StartupServiceRequest) result;
                logBusinessAction("view_startup", Map.of(
                        "startupId", startup.getStartupId(),
                        "name", startup.getName(),
                        "category", startup.getCategory(),
                        "timestamp", viewTime,
                        "hour", viewTime.getHour(),
                        "dayOfWeek", viewTime.getDayOfWeek().toString()
                ));
            }

            return result;
        } catch (Exception e) {
            logBusinessAction("view_startup_failed", Map.of(
                    "startupId", startupId,
                    "error_message", e.getMessage()
            ));
            throw e;
        }
    }

    /**
     * 스타트업 검색 로깅
     */
    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.searchStartupsByKeyword(..))")
    public Object logStartupSearch(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String keyword = (String) args[0];
        LocalDateTime searchTime = LocalDateTime.now();

        try {
            Object result = joinPoint.proceed();

            if (result instanceof List) {
                List<StartupElasticsearch> startups = (List<StartupElasticsearch>) result;

                startups.forEach(startup -> logBusinessAction("search_startup", Map.of(
                        "keyword", keyword,
                        "startupId", startup.getStartupId(),
                        "name", startup.getName(),
                        "category", startup.getCategory(),
                        "timestamp", searchTime,
                        "hour", searchTime.getHour(),
                        "dayOfWeek", searchTime.getDayOfWeek().toString()
                )));
            }

            return result;
        } catch (Exception e) {
            logBusinessAction("search_startup_failed", Map.of(
                    "keyword", keyword,
                    "error_message", e.getMessage()
            ));
            throw e;
        }
    }
}

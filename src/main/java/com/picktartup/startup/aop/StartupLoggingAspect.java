package com.picktartup.startup.aop;

import com.picktartup.startup.dto.StartupElasticsearch;
import com.picktartup.startup.dto.StartupServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class StartupLoggingAspect {

    // 1. API 성능 모니터링
    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.*(..))")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            // 성능 로그 포맷: performance|method=메소드명|time=실행시간|timestamp=시간
            log.info("performance|method={}|time={}ms|timestamp={}",
                    methodName,
                    executionTime,
                    System.currentTimeMillis());

            // 1초 이상 걸리는 경우 경고
            if (executionTime > 1000) {
                log.warn("slow_api|method={}|time={}ms", methodName, executionTime);
            }

            return result;
        } catch (Exception e) {
            log.error("error|method={}|error_type={}|message={}",
                    methodName,
                    e.getClass().getSimpleName(),
                    e.getMessage());
            throw e;
        }
    }


    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.getStartupDetailsFrom*(..))")
    public Object logStartupView(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long startupId = (Long) args[0];
        LocalDateTime viewTime = LocalDateTime.now();

        try {
            Object result = joinPoint.proceed();

            // 스타트업 조회 로그
            if (result instanceof StartupServiceRequest) {
                StartupServiceRequest startup = (StartupServiceRequest) result;
                log.info("business_log|action=view|" +
                                "startupId={}|" +
                                "category={}|" +
                                "name={}|" +
                                "time={}|" +
                                "hour={}|" +
                                "dayOfWeek={}",
                        startupId,
                        startup.getCategory(),
                        startup.getName(),
                        viewTime,
                        viewTime.getHour(),
                        viewTime.getDayOfWeek()
                );
            }

            return result;
        } catch (Exception e) {
            log.error("Error viewing startup: {}", startupId);
            throw e;
        }
    }

    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.searchStartupsByKeyword(..))")
    public Object logSearch(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String keyword = (String) args[0];
        LocalDateTime searchTime = LocalDateTime.now();

        try {
            Object result = joinPoint.proceed();

            // 검색 로그
            log.info("business_log|action=search|" +
                            "keyword={}|" +
                            "time={}|" +
                            "hour={}|" +
                            "dayOfWeek={}",
                    keyword,
                    searchTime,
                    searchTime.getHour(),
                    searchTime.getDayOfWeek()
            );

            // 검색 결과에서 카테고리별 수 집계
            if (result instanceof List) {
                List<StartupElasticsearch> searchResults = (List<StartupElasticsearch>) result;
                Map<String, Long> categoryCounts = searchResults.stream()
                        .collect(Collectors.groupingBy(
                                StartupElasticsearch::getCategory,
                                Collectors.counting()
                        ));

                log.info("business_log|action=search_results|" +
                                "keyword={}|" +
                                "total_results={}|" +
                                "category_distribution={}",
                        keyword,
                        searchResults.size(),
                        categoryCounts
                );
            }

            return result;
        } catch (Exception e) {
            log.error("Error in search with keyword: {}", keyword);
            throw e;
        }
    }

    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.getTop6StartupsByProgress())")
    public Object logTopStartups(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();

            // Top 6 스타트업 분석
            if (result instanceof List) {
                List<StartupServiceRequest> topStartups = (List<StartupServiceRequest>) result;
                Map<String, Long> categoryDistribution = topStartups.stream()
                        .collect(Collectors.groupingBy(
                                StartupServiceRequest::getCategory,
                                Collectors.counting()
                        ));

                log.info("business_log|action=top_startups|" +
                                "time={}|" +
                                "category_distribution={}|" +
                                "top_startup_names={}",
                        LocalDateTime.now(),
                        categoryDistribution,
                        topStartups.stream()
                                .map(StartupServiceRequest::getName)
                                .collect(Collectors.joining(","))
                );
            }

            return result;
        } catch (Exception e) {
            log.error("Error getting top startups");
            throw e;
        }
    }


    // 6. 에러 모니터링
    @AfterThrowing(
            pointcut = "execution(* com.picktartup.startup.service.StartupServiceImpl.*(..))",
            throwing = "error"
    )
    public void logError(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();

        // 에러 로그 포맷: error|method=메소드명|error_type=에러종류|message=메시지|timestamp=시간
        log.error("error|method={}|error_type={}|message={}|timestamp={}",
                methodName,
                error.getClass().getSimpleName(),
                error.getMessage(),
                System.currentTimeMillis());
    }
}

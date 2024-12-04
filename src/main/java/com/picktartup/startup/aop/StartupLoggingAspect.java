package com.picktartup.startup.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picktartup.startup.dto.StartupElasticsearch;
import com.picktartup.startup.dto.StartupServiceRequest;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.stat.internal.StatisticsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Session;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class StartupLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger("API_METRICS");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EntityManager entityManager;

    // API 성능 및 쿼리 모니터링
    @Around("execution(* com.picktartup.startup.controller.*.*(..))")
    public Object logAPIMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Map<String, Object> logData = new HashMap<>();

        // 기본 메타데이터
        logData.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        logData.put("request_id", UUID.randomUUID().toString());

        // API 정보
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        logData.put("http_method", request.getMethod());
        logData.put("uri", request.getRequestURI());
        logData.put("client_ip", request.getRemoteAddr());

        // API 엔드포인트 정보
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logData.put("api_name", signature.getDeclaringType().getSimpleName() + "." + signature.getName());
        logData.put("api_path", request.getRequestURI().replaceAll("/\\d+", "/{id}"));

        // 쿼리 성능 측정
        //int initialQueryCount = getQueryCount();

        try {
            Object result = joinPoint.proceed();

            // 응답 메트릭
            long executionTime = System.currentTimeMillis() - startTime;
            //int finalQueryCount = getQueryCount() - initialQueryCount;

            logData.put("response_time_ms", executionTime);
            logData.put("status", getStatusCode(result));
            logData.put("success", true);

            // 쿼리 성능 메트릭
//            Map<String, Object> queryMetrics = new HashMap<>();
//            queryMetrics.put("query_count", finalQueryCount);
//            queryMetrics.put("avg_query_time_ms", finalQueryCount > 0 ? executionTime / finalQueryCount : 0);
//
//            if (finalQueryCount > 10) {
//                queryMetrics.put("warning", "possible_n_plus_one");
//            }
//            logData.put("query_performance", queryMetrics);

            // 느린 API 경고
            if (executionTime > 1000) {
                logData.put("warning", "slow_api");
            }

            logger.info(objectMapper.writeValueAsString(logData));
            return result;

        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logData.put("response_time_ms", executionTime);
            logData.put("status", getErrorStatusCode(e));
            logData.put("success", false);
            logData.put("error_type", e.getClass().getSimpleName());
            logData.put("error_message", e.getMessage());

            logger.error(objectMapper.writeValueAsString(logData));
            throw e;
        }
    }

    // 비즈니스 로직 모니터링 - 스타트업 조회
    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.getStartupDetailsFrom*(..))")
    public Object logStartupView(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long startupId = (Long) args[0];

        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        logData.put("request_id", UUID.randomUUID().toString());
        logData.put("startup_id", startupId);

        try {
            Object result = joinPoint.proceed();

            if (result instanceof StartupServiceRequest) {
                StartupServiceRequest startup = (StartupServiceRequest) result;
                logData.put("startup_name", startup.getName());
                logData.put("category", startup.getCategory());
                logData.put("status", "success");
                logData.put("time_of_day", LocalDateTime.now().getHour());
                logData.put("day_of_week", LocalDateTime.now().getDayOfWeek().toString());
            }

            logger.info(objectMapper.writeValueAsString(logData));
            return result;

        } catch (Exception e) {
            logData.put("status", "failed");
            logData.put("error_type", e.getClass().getSimpleName());
            logData.put("error_message", e.getMessage());
            logger.error(objectMapper.writeValueAsString(logData));
            throw e;
        }
    }

    // 비즈니스 로직 모니터링 - 검색
    @Around("execution(* com.picktartup.startup.service.StartupServiceImpl.searchStartupsByKeyword(..))")
    public Object logStartupSearch(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String keyword = (String) args[0];

        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        logData.put("request_id", UUID.randomUUID().toString());
        logData.put("search_keyword", keyword);

        try {
            Object result = joinPoint.proceed();

            if (result instanceof List) {
                List<StartupElasticsearch> startups = (List<StartupElasticsearch>) result;
                logData.put("results_count", startups.size());
                logData.put("status", "success");
                logData.put("time_of_day", LocalDateTime.now().getHour());
                logData.put("day_of_week", LocalDateTime.now().getDayOfWeek().toString());

                Map<String, Long> categoryDistribution = startups.stream()
                        .collect(Collectors.groupingBy(
                                StartupElasticsearch::getCategory,
                                Collectors.counting()
                        ));
                logData.put("category_distribution", categoryDistribution);
            }

            logger.info(objectMapper.writeValueAsString(logData));
            return result;

        } catch (Exception e) {
            logData.put("status", "failed");
            logData.put("error_type", e.getClass().getSimpleName());
            logData.put("error_message", e.getMessage());
            logger.error(objectMapper.writeValueAsString(logData));
            throw e;
        }
    }

//    private int getQueryCount() {
//        return (int) ((StatisticsImpl) entityManager.unwrap(Session.class)
//                .getSessionFactory()
//                .getStatistics())
//                .getQueryExecutionCount();
//    }

    private int getStatusCode(Object result) {
        if (result instanceof ResponseEntity) {
            return ((ResponseEntity<?>) result).getStatusCodeValue();
        }
        return 200;
    }

    private int getErrorStatusCode(Exception e) {
        if (e instanceof HttpClientErrorException) {
            return ((HttpClientErrorException) e).getRawStatusCode();
        }
        return 500;
    }
}


package com.picktartup.startup.controller;

import com.picktartup.startup.common.dto.ApiResponse;
import com.picktartup.startup.dto.StartupElasticsearch;
import com.picktartup.startup.dto.StartupServiceRequest;
import com.picktartup.startup.service.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/startups")
@CrossOrigin(origins = "http://localhost:3000")
public class StartupController {

    private final StartupService startupService;

    @Autowired
    public StartupController(StartupService startupService) {
        this.startupService = startupService;
    }

    // 메인 화면: 상위 6개 스타트업 조회

    @GetMapping("health_check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.ok("서비스가 정상 작동 중입니다."));
    }

    @GetMapping("/top")
    public ResponseEntity<Map<String, Object>> getTopStartups() {
        List<StartupServiceRequest> startups = startupService.getTop6StartupsByProgress();
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "상위 6개 스타트업 조회 성공");
        response.put("data", startups);
        return ResponseEntity.ok(response);
    }

    // 스타트업 투자 화면: 전체 리스트 또는 키워드로 검색
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStartups(
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<StartupElasticsearch> startups;

        if (keyword == null || keyword.isEmpty()) {
            // 키워드가 없을 경우 ELK에서 전체 리스트 반환
            startups = startupService.findAllStartupsInElasticsearch();
        } else {
            // 키워드가 있을 경우 해당 키워드로 검색
            startups = startupService.searchStartupsByKeyword(keyword);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "스타트업 리스트 조회에 성공하였습니다.");
        response.put("data", startups);
        return ResponseEntity.ok(response);
    }

    // 상세 조회 API : 메인 페이지 (JPA) 투자 페이지 (Elasticsearch)
    @GetMapping("/{startupId}")
    public ResponseEntity<Map<String, Object>> getStartupDetails(
            @PathVariable Long startupId,
            @RequestParam(value = "source", required = false, defaultValue = "jpa") String source) {

        StartupServiceRequest startupDetails;

        if ("elk".equalsIgnoreCase(source)) {
            startupDetails = startupService.getStartupDetailsFromElasticsearch(startupId);
        } else {
            startupDetails = startupService.getStartupDetailsFromPostgresql(startupId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "스타트업 상세 조회 성공");
        response.put("data", startupDetails);

        return ResponseEntity.ok(response);
    }
}

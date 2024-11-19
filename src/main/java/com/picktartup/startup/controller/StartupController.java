package com.picktartup.startup.controller;

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
public class StartupController {

    private final StartupService startupService;

    @Autowired
    public StartupController(StartupService startupService) {
        this.startupService = startupService;
    }

    // 메인 화면: 상위 6개 스타트업 조회

    @GetMapping("/top")
    public ResponseEntity<Map<String, Object>> getAllStartups() {
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
        List<StartupServiceRequest> startups;


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

}

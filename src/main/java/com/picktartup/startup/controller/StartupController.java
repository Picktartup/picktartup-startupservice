package com.picktartup.startup.controller;

import com.picktartup.startup.dto.StartupServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/startups")
public class StartupController {
    private final com.picktartup.startup.service.StartupService startupService;

    @Autowired
    public StartupController(com.picktartup.startup.service.StartupService startupService) {
        this.startupService = startupService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStartups() {
        List<StartupServiceRequest> startups = startupService.getTop6StartupsByProgress();
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "스타트업 리스트 조회에 성공하였습니다.");
        response.put("data", startups);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getStartups(@RequestParam(value = "keyword", required = false) String keyword) {
        List<StartupServiceRequest> startups;

        if (keyword != null && !keyword.isEmpty()) {
            // keyword가 있는 경우 검색 수행
            startups = startupService.searchStartupsByKeyword(keyword);
        } else {
            // keyword가 없는 경우 전체 리스트 조회
            startups = startupService.getAllStartups();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", keyword != null ? "스타트업 검색 성공" : "스타트업 리스트 조회에 성공하였습니다.");
        response.put("data", startups);

        return ResponseEntity.ok(response);
    }


}

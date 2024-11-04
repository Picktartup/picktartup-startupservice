package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupServiceRequest;

import java.util.List;

public interface StartupService {
    List<com.picktartup.startup.dto.StartupServiceRequest> getTop6StartupsByProgress();
    List<StartupServiceRequest> getAllStartups(); // 전체 리스트 조회
    List<StartupServiceRequest> searchStartupsByKeyword(String keyword); // 키워드로 검색
}

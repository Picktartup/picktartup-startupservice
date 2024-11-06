package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupServiceRequest;
import com.picktartup.startup.entity.Startup;

import java.util.List;

public interface StartupService {
    List<com.picktartup.startup.dto.StartupServiceRequest> getTop6StartupsByProgress();
    List<StartupServiceRequest> searchStartupsByKeyword(String keyword);
    List<StartupServiceRequest> findAllStartupsInElasticsearch(); // 전체 스타트업 조회 (ELK)

}

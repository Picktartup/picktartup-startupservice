package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupElasticsearch;
import com.picktartup.startup.dto.StartupServiceRequest;

import java.util.List;

public interface StartupService {
    List<StartupServiceRequest> getTop6StartupsByProgress();
    List<StartupElasticsearch> searchStartupsByKeyword(String keyword);
    List<StartupElasticsearch> findAllStartupsInElasticsearch();

    StartupServiceRequest getStartupDetailsFromPostgresql(Long startupId);
    StartupServiceRequest getStartupDetailsFromElasticsearch(Long startupId);

    //String uploadLogo(Long startupId, MultipartFile file);
    //void deleteLogo(Long startupId);
    List<StartupServiceRequest> getAllStartupsWithLogoUrl();
}

package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupServiceRequest;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.repository.StartupServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartupServiceImpl implements StartupService {

    private final StartupServiceRepository startupRepository;

    @Autowired
    public StartupServiceImpl(StartupServiceRepository startupRepository) {
        this.startupRepository = startupRepository;
    }


    public List<StartupServiceRequest> getTop6StartupsByProgress() {
        List<Startup> startups = startupRepository.findTop6ByOrderByProgressDesc();
        return startups.stream()
                .map(this::convertToServiceRequest)
                .collect(Collectors.toList());
    }

    // 전체 스타트업 리스트 조회
    @Override
    public List<StartupServiceRequest> getAllStartups() {
        List<Startup> startups = startupRepository.findAll();
        return startups.stream()
                .map(this::convertToServiceRequest)
                .collect(Collectors.toList());
    }

    // 키워드로 스타트업 검색
    @Override
    public List<StartupServiceRequest> searchStartupsByKeyword(String keyword) {
        List<Startup> startups = startupRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return startups.stream()
                .map(this::convertToServiceRequest)
                .collect(Collectors.toList());
    }

    private StartupServiceRequest convertToServiceRequest(Startup startup) {
        int progress = Integer.parseInt(startup.getProgress().replace("%", ""));
        return StartupServiceRequest.builder()
                .name(startup.getName())
                .category(startup.getCategory())
                .contractStartDate(startup.getContractStartDate()) // LocalDateTime 그대로 사용
                .contractTargetDeadline(startup.getContractTargetDeadline()) // LocalDateTime 그대로 사용
                .progress(progress)
                .currentCoin(startup.getCurrentCoin())
                .build();
    }

}

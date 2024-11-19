package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupServiceRequest;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.entity.ElasticsearchStartupEntity;
import com.picktartup.startup.repository.elasticsearch.StartupElasticsearchRepository;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StartupServiceImpl implements StartupService {

    private final StartupServiceRepository startupRepository;
    private final StartupElasticsearchRepository startupElasticsearchRepository;
    private final RestTemplate restTemplate; // RestTemplate 필드 추가

    @Autowired
    public StartupServiceImpl(StartupServiceRepository startupRepository,
                              StartupElasticsearchRepository startupElasticsearchRepository,
                              RestTemplate restTemplate) {
        this.startupRepository = startupRepository;
        this.startupElasticsearchRepository = startupElasticsearchRepository;
        this.restTemplate = restTemplate;

    }

    @Override
    public List<StartupServiceRequest> getTop6StartupsByProgress() {
        List<Startup> startups = startupRepository.findTop6ByOrderByProgressDesc();
        return startups.stream()
                .map(this::convertJpaToServiceRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<StartupServiceRequest> searchStartupsByKeyword(String keyword) {

        restTemplate.postForEntity("http://localhost:9200/startups/_refresh", null, String.class);


        return startupElasticsearchRepository
                .findByNameContainingOrDescriptionContaining(keyword, keyword)
                .stream()
                .map(this::convertElasticsearchToServiceRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<StartupServiceRequest> findAllStartupsInElasticsearch() {
        return StreamSupport.stream(startupElasticsearchRepository.findAll().spliterator(), false)
                .map(this::convertElasticsearchToServiceRequest)
                .collect(Collectors.toList());
    }

    // JPA 엔티티용 변환 메서드
    private StartupServiceRequest convertJpaToServiceRequest(Startup startup) {
        return StartupServiceRequest.builder()
                .name(startup.getName())
                .category(startup.getCategory())
                .contractStartDate(startup.getContractStartDate())
                .contractTargetDeadline(startup.getContractTargetDeadline())
                .progress((startup.getProgress() != null) ? startup.getProgress() : "0%")
                .currentCoin(startup.getCurrentCoin() != null ? startup.getCurrentCoin() : 0)
                .goalCoin(startup.getGoalCoin() != null ? startup.getGoalCoin() : 0)
                .fundingProgress(calculateFundingProgress(startup.getCurrentCoin(), startup.getGoalCoin()))
                .build();
    }

    // Elasticsearch 엔티티용 변환 메서드
    private StartupServiceRequest convertElasticsearchToServiceRequest(ElasticsearchStartupEntity startup) {
        return StartupServiceRequest.builder()
                .name(startup.getName())
                .category(startup.getCategory())
                .contractStartDate(startup.getContractStartDate())
                .contractTargetDeadline(startup.getContractTargetDeadline())
                .progress((startup.getProgress() != null) ? startup.getProgress() : "0%")
                .currentCoin(startup.getCurrentCoin() != null ? startup.getCurrentCoin() : 0)
                .goalCoin(startup.getGoalCoin() != null ? startup.getGoalCoin() : 0)
                .fundingProgress(calculateFundingProgress(startup.getCurrentCoin(), startup.getGoalCoin()))
                .build();
    }

    // FundingProgress 계산 메서드
    private int calculateFundingProgress(Integer currentCoin, Integer goalCoin) {
        return (goalCoin != null && goalCoin > 0)
                ? (int) ((currentCoin != null ? (double) currentCoin : 0) / goalCoin * 100)
                : 0;
    }
}

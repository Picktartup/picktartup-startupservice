package com.picktartup.startup.service;

import com.picktartup.startup.dto.*;
import com.picktartup.startup.entity.SSI;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.repository.elasticsearch.SSIElasticsearchRepository;
import com.picktartup.startup.repository.elasticsearch.StartupElasticsearchRepository;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StartupServiceImpl implements StartupService {

    private final StartupServiceRepository startupRepository;
    private final StartupElasticsearchRepository startupElasticsearchRepository;
    private final SSIElasticsearchRepository ssiElasticsearchRepository;

    @Autowired
    public StartupServiceImpl(
            StartupServiceRepository startupRepository,
            StartupElasticsearchRepository startupElasticsearchRepository,
            SSIElasticsearchRepository ssiElasticsearchRepository) {
        this.startupRepository = startupRepository;
        this.startupElasticsearchRepository = startupElasticsearchRepository;
        this.ssiElasticsearchRepository = ssiElasticsearchRepository;
    }

    @Override
    public List<StartupServiceRequest> getTop6StartupsByProgress() {
        return startupRepository.findAll().stream()
                .map(this::convertJpaToDto)
                .sorted((s1, s2) -> Integer.compare(s2.getFundingProgress(), s1.getFundingProgress()))
                .limit(6)
                .collect(Collectors.toList());
    }

    @Override
    public List<StartupElasticsearch> findAllStartupsInElasticsearch() {
        List<StartupElasticsearch> startups = StreamSupport
                .stream(startupElasticsearchRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // Calculate funding progress for each startup
        startups.forEach(StartupElasticsearch::calculateAndSetFundingProgress);
        return startups;
    }

    @Override
    public List<StartupElasticsearch> searchStartupsByKeyword(String keyword) {
        List<StartupElasticsearch> startups = startupElasticsearchRepository
                .findByNameContainingOrDescriptionContaining(keyword, keyword);

        // Calculate funding progress for each startup
        startups.forEach(StartupElasticsearch::calculateAndSetFundingProgress);
        return startups;
    }

    @Override
    public StartupServiceRequest getStartupDetailsFromPostgresql(Long startupId) {
        return startupRepository.findById(startupId)
                .map(this::convertJpaToDto)
                .orElseThrow(() -> new RuntimeException("Startup not found"));
    }

    @Override
    public StartupServiceRequest getStartupDetailsFromElasticsearch(Long startupId) {
        StartupElasticsearch elasticsearchStartup = startupElasticsearchRepository.findById(startupId)
                .orElseThrow(() -> new RuntimeException("Startup not found"));

        List<SSIServiceRequest> ssiList = ssiElasticsearchRepository.findByStartupId(startupId).stream()
                .map(this::convertElasticsearchSsiToDto)
                .collect(Collectors.toList());

        // Calculate funding progress
        elasticsearchStartup.calculateAndSetFundingProgress();

        return convertElasticsearchToServiceRequest(elasticsearchStartup, ssiList);
    }

    private StartupServiceRequest convertJpaToDto(Startup startup) {
        return StartupServiceRequest.builder()
                .startupId(startup.getStartupId())
                .name(startup.getName())
                .description(startup.getDescription())
                .category(startup.getCategory())
                .investmentStartDate(startup.getInvestmentStartDate())
                .investmentTargetDeadline(startup.getInvestmentTargetDeadline())
                .progress(startup.getProgress())
                .currentCoin(startup.getCurrentCoin())
                .goalCoin(startup.getGoalCoin())
                .fundingProgress(calculateFundingProgress(startup.getCurrentCoin(), startup.getGoalCoin()))
                .ssiList(startup.getSsi().stream()
                        .map(this::convertSsiToDto)
                        .collect(Collectors.toList()))
                .investmentRound(startup.getInvestmentRound())
                .investmentStatus(startup.getInvestmentStatus())
                .ceoName(startup.getCeoName())
                .address(startup.getAddress())
                .page(startup.getPage())
                .establishmentDate(startup.getEstablishmentDate())
                .build();
    }

    private StartupServiceRequest convertElasticsearchToServiceRequest(StartupElasticsearch startup, List<SSIServiceRequest> ssiList) {
        return StartupServiceRequest.builder()
                .startupId(startup.getStartupId())
                .name(startup.getName())
                .description(startup.getDescription())
                .category(startup.getCategory())
                .investmentStartDate(startup.getInvestmentStartDate())
                .investmentTargetDeadline(startup.getInvestmentTargetDeadline())
                .progress(startup.getProgress())
                .currentCoin(startup.getCurrentCoin())
                .goalCoin(startup.getGoalCoin())
                .fundingProgress(startup.getFundingProgress())
                .ssiList(ssiList)
                .ceoName(startup.getCeoName())
                .address(startup.getAddress())
                .page(startup.getPage())
                .establishmentDate(startup.getEstablishmentDate())
                .build();
    }

    private SSIServiceRequest convertElasticsearchSsiToDto(SSIElasticsearch ssiElasticsearch) {
        return SSIServiceRequest.builder()
                .ssiId(ssiElasticsearch.getSsiId())
                .peopleGrade(ssiElasticsearch.getPeopleGrade())
                .productGrade(ssiElasticsearch.getProductGrade())
                .performanceGrade(ssiElasticsearch.getPerformanceGrade())
                .potentialGrade(ssiElasticsearch.getPotentialGrade())
                .evalDate(ssiElasticsearch.getEvalDate())
                .evalDescription(ssiElasticsearch.getEvalDescription())
                .build();
    }

    private SSIServiceRequest convertSsiToDto(SSI ssi) {
        return SSIServiceRequest.builder()
                .ssiId(ssi.getSsiId())
                .peopleGrade(ssi.getPeopleGrade())
                .productGrade(ssi.getProductGrade())
                .performanceGrade(ssi.getPerformanceGrade())
                .potentialGrade(ssi.getPotentialGrade())
                .evalDate(ssi.getEvalDate())
                .evalDescription(ssi.getEvalDescription())
                .build();
    }

    private int calculateFundingProgress(Integer currentCoin, Integer goalCoin) {
        if (currentCoin == null || goalCoin == null || goalCoin == 0) {
            return 0;
        }
        return (int) ((double) currentCoin / goalCoin * 100);
    }
}
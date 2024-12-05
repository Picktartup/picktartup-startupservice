package com.picktartup.startup.service;

import com.amazonaws.services.s3.AmazonS3;
import com.picktartup.startup.dto.*;
import com.picktartup.startup.entity.SSI;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.entity.StartupAnnualMetrics;
import com.picktartup.startup.entity.StartupMonthlyMetrics;
import com.picktartup.startup.repository.elasticsearch.SSIElasticsearchRepository;
import com.picktartup.startup.repository.elasticsearch.StartupElasticsearchRepository;
import com.picktartup.startup.repository.jpa.StartupMetricsRepository;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class StartupServiceImpl implements StartupService {

    private final StartupServiceRepository startupRepository;
    private final StartupElasticsearchRepository startupElasticsearchRepository;
    private final SSIElasticsearchRepository ssiElasticsearchRepository;
    private final AmazonS3 amazonS3;
    private final String s3Bucket;
    private final String awsRegion;

    private final StartupMetricsRepository metricsRepository;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public StartupServiceImpl(
            StartupServiceRepository startupRepository,
            StartupElasticsearchRepository startupElasticsearchRepository,
            SSIElasticsearchRepository ssiElasticsearchRepository,
            AmazonS3 amazonS3,
            @Value("${cloud.aws.s3.bucket}") String s3Bucket,
            @Value("${cloud.aws.region.static}") String awsRegion,
            StartupMetricsRepository metricsRepository) {
        this.startupRepository = startupRepository;
        this.startupElasticsearchRepository = startupElasticsearchRepository;
        this.ssiElasticsearchRepository = ssiElasticsearchRepository;
        this.amazonS3 = amazonS3; // AmazonS3 주입
        this.s3Bucket = s3Bucket;
        this.awsRegion = awsRegion;
        this.metricsRepository = metricsRepository;
    }

    @Override
    public List<StartupServiceRequest> getTop6StartupsByProgress() {
        return startupRepository.findAll().stream()
                .sorted((a, b) -> calculateFundingProgress(b.getCurrentCoin(), b.getGoalCoin())
                        - calculateFundingProgress(a.getCurrentCoin(), a.getGoalCoin()))
                .limit(6)
                .map(this::convertJpaToDto)
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
                .description(startup.getStartupDetails().getDescription())
                .category(startup.getCategory())
                .investmentStartDate(startup.getInvestmentStartDate())
                .investmentTargetDeadline(startup.getInvestmentTargetDeadline())
                .progress(startup.getProgress() != null ? startup.getProgress().toString() : "0")  // Integer -> String 변환
                .currentCoin(startup.getCurrentCoin())
                .goalCoin(startup.getGoalCoin())
                .fundingProgress(startup.getFundingProgress() != null ?
                        startup.getFundingProgress() :
                        calculateFundingProgress(startup.getCurrentCoin(), startup.getGoalCoin()))
                .ssiList(startup.getSsi().stream()
                        .map(this::convertSsiToDto)
                        .collect(Collectors.toList()))
                .investmentStatus(startup.getStartupDetails().getInvestmentStatus())
                .ceoName(startup.getStartupDetails().getCeoName())
                .address(startup.getStartupDetails().getAddress())
                .page(startup.getStartupDetails().getPage())
                .establishmentDate(startup.getStartupDetails().getEstablishmentDate())
                .current_round(startup.getStartupDetails().getCurrentRound())
                .registration_num(startup.getStartupDetails().getRegistrationNum())
                .contract_period(startup.getStartupDetails().getContractPeriod())
                .signature(startup.getStartupDetails().getSignature())
                .ceo_user_id(startup.getCeoUserId())
                .industry_type(startup.getIndustryType())
                .campaign_id(startup.getCampaignId())
                .expected_roi(startup.getStartupDetails().getExpectedRoi())
                .roi(startup.getStartupDetails().getRoi())
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
                .current_round(startup.getInvestmentRound())
                .industry_type(startup.getIndustryType())
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

    private int calculateFundingProgress(Double currentCoin, Integer goalCoin) {
        if (currentCoin == null || goalCoin == null || goalCoin == 0) {
            return 0;
        }
        return (int) ((double) currentCoin / goalCoin * 100);
    }


    public List<StartupServiceRequest> getAllStartupsWithLogoUrl() {
        return startupRepository.findAll().stream()
                .map(startup -> StartupServiceRequest.builder()
                        .startupId(startup.getStartupId())
                        .name(startup.getName())
                        .logoUrl(String.format("https://%s.s3.%s.amazonaws.com/%s.png",
                                s3Bucket,
                                awsRegion,
                                startup.getName().toLowerCase()))
                        .industry_type(startup.getIndustryType())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnualMetricsResponse> getAnnualMetrics(Long startupId) {
        List<StartupAnnualMetrics> metrics = metricsRepository.findByStartup_StartupIdOrderByYear(startupId);

        return metrics.stream()
                .map(metric -> {
                    AnnualMetricsResponse response = new AnnualMetricsResponse();
                    response.setAnnualId(metric.getAnnualId());
                    response.setYear(metric.getYear());
                    response.setAnnualRevenue(metric.getAnnualRevenue());
                    response.setOperatingProfit(metric.getOperatingProfit());
                    response.setTotalAsset(metric.getTotalAsset());
                    response.setNetProfit(metric.getNetProfit());
                    response.setCreatedAt(metric.getCreatedAt());
                    response.setDataSource(metric.getDataSource());
                    response.setStartupId(metric.getStartup().getStartupId());
                    response.setInvestmentRound(metric.getInvestmentRound());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyMetricsResponse> getMonthlyMetrics(Long startupId) {
        List<StartupMonthlyMetrics> metrics = metricsRepository.findMonthlyByStartupIdOrderByMetricDate(startupId);

        return metrics.stream()
                .map(metric -> {
                    MonthlyMetricsResponse response = new MonthlyMetricsResponse();
                    response.setMonthlyId(metric.getMonthlyId());
                    response.setMetricDate(metric.getMetricDate());
                    response.setMau(metric.getMau());
                    response.setEmployeeCount(metric.getEmployeeCount());
                    response.setCreatedAt(metric.getCreatedAt());
                    response.setDataSource(metric.getDataSource());
                    response.setStartupId(metric.getStartup().getStartupId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MetricsChartResponse> getMetricsForChart(Long startupId, String period) {
        if ("annual".equals(period)) {
            List<StartupAnnualMetrics> metrics = metricsRepository.findByStartup_StartupIdOrderByYear(startupId);
            return metrics.stream()
                    .map(metric -> {
                        MetricsChartResponse response = new MetricsChartResponse();
                        response.setDate(String.valueOf(metric.getYear()));
                        response.setRevenue(metric.getAnnualRevenue());
                        response.setOperatingProfit(metric.getOperatingProfit());
                        response.setNetProfit(metric.getNetProfit());
                        return response;
                    })
                    .collect(Collectors.toList());
        } else {
            List<StartupMonthlyMetrics> metrics = metricsRepository.findMonthlyByStartupIdOrderByMetricDate(startupId);
            return metrics.stream()
                    .map(metric -> {
                        MetricsChartResponse response = new MetricsChartResponse();
                        response.setDate(metric.getMetricDate().format(DateTimeFormatter.ofPattern("yyyy-MM")));
                        response.setMau(metric.getMau());
                        response.setEmployeeCount(metric.getEmployeeCount());
                        return response;
                    })
                    .collect(Collectors.toList());
        }
    }


    @Transactional
    @Override
    public void updateCampaignId(SetCampaignIdRequest request) {
        Startup startup = startupRepository.findById(request.getStartupId())
                .orElseThrow(() -> new RuntimeException("Startup not found with ID: " + request.getStartupId()));

        startup.setCampaignId(request.getCampaignId());
        startupRepository.save(startup);

        log.info("Updated campaignId for startupId {}: {}", request.getStartupId(), request.getCampaignId());
    }



}
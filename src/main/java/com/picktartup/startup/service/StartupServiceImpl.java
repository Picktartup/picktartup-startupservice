package com.picktartup.startup.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.picktartup.startup.dto.*;
import com.picktartup.startup.entity.SSI;
import com.picktartup.startup.entity.Startup;
import com.picktartup.startup.repository.elasticsearch.SSIElasticsearchRepository;
import com.picktartup.startup.repository.elasticsearch.StartupElasticsearchRepository;
import com.picktartup.startup.repository.jpa.StartupServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
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


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public StartupServiceImpl(
            StartupServiceRepository startupRepository,
            StartupElasticsearchRepository startupElasticsearchRepository,
            SSIElasticsearchRepository ssiElasticsearchRepository,
            AmazonS3 amazonS3,
            @Value("${cloud.aws.s3.bucket}") String s3Bucket,
            @Value("${cloud.aws.region.static}") String awsRegion) {
        this.startupRepository = startupRepository;
        this.startupElasticsearchRepository = startupElasticsearchRepository;
        this.ssiElasticsearchRepository = ssiElasticsearchRepository;
        this.amazonS3 = amazonS3; // AmazonS3 주입
        this.s3Bucket = s3Bucket;
        this.awsRegion = awsRegion;
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
                .investmentRound(startup.getStartupDetails().getInvestmentRound())
                .investmentStatus(startup.getStartupDetails().getInvestmentStatus())
                .ceoName(startup.getStartupDetails().getCeoName())
                .address(startup.getStartupDetails().getAddress())
                .page(startup.getStartupDetails().getPage())
                .establishmentDate(startup.getStartupDetails().getEstablishmentDate())
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

    private int calculateFundingProgress(Double currentCoin, Integer goalCoin) {
        if (currentCoin == null || goalCoin == null || goalCoin == 0) {
            return 0;
        }
        return (int) ((double) currentCoin / goalCoin * 100);
    }


//    public String uploadLogo(Long startupId, MultipartFile file) {
//        try {
//            Startup startup = startupRepository.findById(startupId)
//                    .orElseThrow(() -> new IllegalArgumentException("Startup not found"));
//
//            String fileName = startup.getName().toLowerCase() + UUID.randomUUID() + ".png";
//            String fileUrl = "startup-logos/" + fileName;
//
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//
//            // S3에 업로드
//            amazonS3Client.putObject(
//                    new PutObjectRequest(bucket, fileUrl, file.getInputStream(), metadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead)
//            );
//
//            // URL 저장
//            String logoUrl = amazonS3Client.getUrl(bucket, fileUrl).toString();
//            startup.setLogoUrl(logoUrl);
//            startupRepository.save(startup);
//
//            return logoUrl;
//        } catch (IOException e) {
//            log.error("로고 업로드 실패: {}", e.getMessage());
//            throw new RuntimeException("로고 업로드에 실패했습니다.");
//        }
//    }
//
//    // 로고 삭제 메서드 추가
//    public void deleteLogo(Long startupId) {
//        Startup startup = startupRepository.findById(startupId)
//                .orElseThrow(() -> new IllegalArgumentException("Startup not found"));
//
//        if (startup.getLogoUrl() != null) {
//            String fileUrl = startup.getLogoUrl();
//            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//            amazonS3Client.deleteObject(bucket, "startup-logos/" + fileName);
//
//            startup.setLogoUrl(null);
//            startupRepository.save(startup);
//        }
//    }

    public List<StartupServiceRequest> getAllStartupsWithLogoUrl() {
        return startupRepository.findAll().stream()
                .map(startup -> StartupServiceRequest.builder()
                        .startupId(startup.getStartupId())
                        .name(startup.getName())
                        .logoUrl(String.format("https://%s.s3.%s.amazonaws.com/%s.png",
                                s3Bucket,
                                awsRegion,
                                startup.getName().toLowerCase()))
                        .build())
                .collect(Collectors.toList());
    }
}
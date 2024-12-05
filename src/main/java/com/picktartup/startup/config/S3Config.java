package com.picktartup.startup.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class S3Config {

    // AWS 관련 설정값 주입
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    //AmazonS3 클라이언트 Bean 생성
    @Bean
    public AmazonS3 amazonS3Client() {
        if (accessKey == null || secretKey == null || region == null) {
            throw new IllegalArgumentException("AWS 환경 변수가 올바르게 설정되지 않았습니다.");
        }

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }


     //S3 버킷 이름을 Bean으로 등록
    @Bean
    public String s3Bucket() {
        return bucket;
    }


     //AWS 리전 이름을 Bean으로 등록
    @Bean
    public String awsRegion() {
        return region;
    }
}

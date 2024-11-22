package com.picktartup.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.picktartup.startup.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.picktartup.startup.repository.elasticsearch")
public class StartupApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartupApplication.class, args);
	}

	@Bean
	CommandLineRunner testMongoConnection(MongoTemplate mongoTemplate) {
		return args -> {
			System.out.println("MongoDB 연결 테스트 시작...");
			System.out.println("MongoDB 데이터베이스 이름: " + mongoTemplate.getDb().getName());
		};
	}
}

package com.picktartup.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.picktartup.startup.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.picktartup.startup.repository.elasticsearch")
@EnableScheduling
@EnableAspectJAutoProxy
public class StartupApplication {

	public static void main(String[] args) {

		SpringApplication.run(StartupApplication.class, args);

	}
}

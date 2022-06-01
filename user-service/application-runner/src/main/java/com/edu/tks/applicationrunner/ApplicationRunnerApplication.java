package com.edu.tks.applicationrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.edu.tks.rest",
                "com.edu.tks.soap",
                "com.edu.tks.service",
                "com.edu.tks.repo",
                "com.edu.tks.*"
        }
)
//@EnableEurekaClient
public class ApplicationRunnerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunnerApplication.class, args);
    }


    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}

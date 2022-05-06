package com.edu.tks.applicationrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.edu.tks.rest",
                "com.edu.tks.soap",
                "com.edu.tks.service",
                "com.edu.tks.repo",
                "com.edu.tks.*"
        }
)
public class ApplicationRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunnerApplication.class, args);
    }

}

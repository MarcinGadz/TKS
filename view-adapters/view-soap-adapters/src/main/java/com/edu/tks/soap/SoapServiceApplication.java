package com.edu.tks.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.edu.tks.*"
        }
)
public class SoapServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoapServiceApplication.class, args);
    }
}

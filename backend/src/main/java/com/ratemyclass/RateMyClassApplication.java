package com.ratemyclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class RateMyClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateMyClassApplication.class, args);
    }
}
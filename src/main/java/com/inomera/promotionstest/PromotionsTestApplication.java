package com.inomera.promotionstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PromotionsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionsTestApplication.class, args);
    }

}

package com.biz.fastcampus.pharmacyrecommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FastcampusPharmacyRecommendationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastcampusPharmacyRecommendationApplication.class, args);
    }

}

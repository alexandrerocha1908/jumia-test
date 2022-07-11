package com.test.jumia.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class EcommerceCheckoutApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceCheckoutApplication.class, args);
    }
}

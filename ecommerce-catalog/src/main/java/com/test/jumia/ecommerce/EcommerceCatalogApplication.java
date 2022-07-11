package com.test.jumia.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcommerceCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceCatalogApplication.class, args);
    }

}

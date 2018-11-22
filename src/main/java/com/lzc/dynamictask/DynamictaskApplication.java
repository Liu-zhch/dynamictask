package com.lzc.dynamictask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DynamictaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamictaskApplication.class, args);
    }
}

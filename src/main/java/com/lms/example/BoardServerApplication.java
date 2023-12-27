package com.lms.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BoardServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardServerApplication.class, args);
    }

}

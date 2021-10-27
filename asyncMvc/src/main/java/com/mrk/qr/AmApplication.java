package com.mrk.qr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmApplication.class, args);
    }
}

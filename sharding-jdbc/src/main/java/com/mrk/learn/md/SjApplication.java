package com.mrk.learn.md;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjApplication.class, args);
    }

}

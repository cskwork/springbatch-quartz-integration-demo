package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo")
public class BatchQuartzDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchQuartzDemoApplication.class, args);
    }
}

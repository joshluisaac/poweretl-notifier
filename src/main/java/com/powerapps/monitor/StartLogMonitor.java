package com.powerapps.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartLogMonitor {

    public static void main(String[] args) {
        SpringApplication.run(StartLogMonitor.class, args);
    }

}
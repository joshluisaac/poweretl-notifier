package com.powerapps.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.powerapps.monitor.config.EmailConfigAssembler;



@SpringBootApplication
@EnableScheduling
public class StartLogMonitor {

    public static void main(String[] args) {
        SpringApplication.run(StartLogMonitor.class, args);
    }

}
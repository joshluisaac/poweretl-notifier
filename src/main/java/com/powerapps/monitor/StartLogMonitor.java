package com.powerapps.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.powerapps.monitor.config.EmailConfigAssembler;



@SpringBootApplication
@EnableScheduling
@ComponentScan({"org.springframework.mail.javamail","com.kollect.etl.notification.config","com.kollect.etl.notification.service","com.kollect.etl.notification.entity"})
@Import(EmailConfigAssembler.class)
public class StartLogMonitor {

    public static void main(String[] args) {
        SpringApplication.run(StartLogMonitor.class, args);
    }

}
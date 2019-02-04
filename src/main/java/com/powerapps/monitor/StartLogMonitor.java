package com.powerapps.monitor;

import com.powerapps.monitor.dataconnector.ScheduledTaskExecutorNative;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartLogMonitor {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(StartLogMonitor.class, args);
        //publishDcEmail(context);

    }


    private static void publishDcEmail(ConfigurableApplicationContext context){
        ScheduledTaskExecutorNative task = context.getBean(ScheduledTaskExecutorNative.class);
        task.execute();
    }

}
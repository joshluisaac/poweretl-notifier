package com.powerapps.monitor.tasks;

import com.powerapps.monitor.service.BatchManagerLogService;
import com.powerapps.monitor.service.DataConnectorNotification;
import com.powerapps.monitor.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class runs the core scheduling tasks. It takes in the time values from the properties
 * file and runs a scheduled task.
 *
 * @author hashim
 */
@Component
@PropertySource("classpath:config/fixedSchedulerConfig.properties")
public class Scheduler {
    /*Required services*/
    private final BatchManagerLogService bmService;
    private final DataConnectorNotification dcNotificationService;
    /*Required variables*/
    @Value("${app.dcServerLogPath}")
    String dcServerLogPath;
    private final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    /*The constructor for the class to inject the necessary services*/
    @Autowired
    public Scheduler(BatchManagerLogService bmService,
                     
                     DataConnectorNotification dcNotificationService){
        this.bmService=bmService;
        this.dcNotificationService=dcNotificationService;
    }

    //@Scheduled(fixedRateString = "${interval}")
    public void runFixedSchedule() throws IOException {
        bmService.emailAndPersistToCache();
    }

    @Scheduled(fixedRateString = "${interval}")
    public void sendDataConnectorStatsEmail() throws IOException {
        String title = "MBSB - Daily Data Loading";
        String context = "mbsb";
        dcNotificationService.execute(title, dcServerLogPath, context);
    }
}
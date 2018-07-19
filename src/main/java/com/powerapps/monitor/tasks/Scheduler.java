package com.powerapps.monitor.tasks;

import com.powerapps.monitor.config.JsonToHashMap;
import com.powerapps.monitor.service.BatchManagerLogService;
import com.powerapps.monitor.service.EmailClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Scheduler {
    /*Required services*/
    private final BatchManagerLogService bmService;
    private final EmailClient emailClient;
    private final JsonToHashMap jsonToHashMap;
    /*Required variables*/
    @Value("${app.fixedSchedulerJson}")
    private String fixedSchedulerJsonPath;
    private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
    /*The constructor for the class to inject the necessary services*/
    @Autowired
    public Scheduler(BatchManagerLogService bmService,
                     EmailClient emailClient,
                     JsonToHashMap jsonToHashMap){
        this.bmService=bmService;
        this.emailClient=emailClient;
        this.jsonToHashMap=jsonToHashMap;
    }

    @Scheduled(fixedRate = 120000L)
    public void runFixedSchedule() throws IOException {
        bmService.emailAndPersistToCache();
    }
}
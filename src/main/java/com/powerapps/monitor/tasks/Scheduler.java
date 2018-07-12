package com.powerapps.monitor.tasks;

import com.powerapps.monitor.service.BatchManagerLogService;
import com.powerapps.monitor.service.EmailClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Scheduler {
  
  private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
  
  @Autowired
  private EmailClient emailClient;
  @Autowired
  private BatchManagerLogService bmService;
    /*Required services*/

    /*Required variables*/

    /*The constructor for the class to inject the necessary services*/

    @Scheduled(fixedDelay = 120000)
    public void runFixedSchedule() throws IOException {
        bmService.emailAndPersistToCache();
    }
}
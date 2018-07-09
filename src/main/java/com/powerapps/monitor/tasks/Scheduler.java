package com.powerapps.monitor.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    /*Required services*/

    /*Required variables*/

    /*The constructor for the class to inject the necessary services*/

    @Scheduled(fixedDelay = 120000)
    public void runDummy() {
        System.out.println(
                "Fixed delay task every 2 minutes");
    }
}
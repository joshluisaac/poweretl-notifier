package com.powerapps.monitor.com.powerapps.monitor.dsl

import com.powerapps.monitor.model.LogSummary
import com.powerapps.monitor.service.BatchManagerLogNotificationService
import com.powerapps.monitor.service.BatchManagerLogService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SomeClass {

    private static final Logger LOG = LoggerFactory.getLogger(SomeClass.class)
    BatchManagerLogNotificationService notifService

    BatchManagerLogService logService

    @Autowired
    SomeClass(BatchManagerLogNotificationService notifService, BatchManagerLogService logService){
       this.notifService = notifService
        this.logService = logService
        println("Initialized notification service")
        LOG.info("This is some class being logged here")
        execute()
    }



    void execute(){
        List<LogSummary> l = logService.getBmLogSummaries()
        println l.size()
    }


}

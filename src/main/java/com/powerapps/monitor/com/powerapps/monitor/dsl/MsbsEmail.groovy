package com.powerapps.monitor.com.powerapps.monitor.dsl

import com.powerapps.monitor.model.LogSummary
import com.powerapps.monitor.service.BatchManagerLogNotificationService
import com.powerapps.monitor.service.BatchManagerLogService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service




@Service
class MbsbEmail {

    private static final Logger LOG = LoggerFactory.getLogger(MbsbEmail.class)
    private final BatchManagerLogService logService;
    private final BatchManagerLogNotificationService notifService


    @Autowired
    MbsbEmail(BatchManagerLogNotificationService notifService){
       this.notifService = notifService
        if (notifService == null) {
            println "Object is null"
        }
        println("Initialized notification service")
    }

    @Value(value=Constants.MAX_QUEUE_SIZE)
    public String emailMaxQueueSize

    @Value(value = Constants.EMAIL_TITLE)
    String emailTitle


    void sendEmail(){
        if (notifService == null) {
            println("Null object")
        }

        List<LogSummary> summaries = notifService.logService.getBmLogSummaries()

        //List<LogSummary> summaries = logService.getBmLogSummaries()
        //BmProperties bmConfig = logService.bmConfig

        /*Build email content*/
        String emailContent = null
        String title = null

        int queueMaxSize = Integer.parseInt(emailMaxQueueSize);
        LOG.info("Processing {} of {} total Batch Manager logs", queueMaxSize,summaries.size())
        LOG.info("Queue max size: {}", queueMaxSize)

        println summaries.size()
    }

    static void main(String[] args) {
        MbsbEmail mbsbEmail = new MbsbEmail()
        mbsbEmail.sendEmail()
    }


}
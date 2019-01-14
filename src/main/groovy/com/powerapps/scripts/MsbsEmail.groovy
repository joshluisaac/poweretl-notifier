package com.powerapps.scripts
import com.kollect.etl.notification.entity.Email
import com.powerapps.monitor.model.BmProperties
import com.powerapps.monitor.model.LogSummary
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value


class MbsbEmail {

    private static final Logger LOG = LoggerFactory.getLogger(MbsbEmail.class)


    @Value("${app.emailMaxQueueSize}")
    public String emailMaxQueueSize

    //@Value("${app.bm.emailTitle}")
    String emailTitle

    void processString() {
        def x = "This is groovy"
        int len = x.length()
        println len
    }

    void processList() {
        def customers = [0, -1, -199, 1, 2, 3, 4, 5, 9]

        println(customers.get(4))
    }

    void 'list processing'(){
        def customers = ["joshua","zoe","andrew","sam","john"]
        println customers.getClass()

    }


    void sendEmail(){
        List<LogSummary> summaries = logService.getBmLogSummaries()
        BmProperties bmConfig = logService.bmConfig

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
        mbsbEmail.processString()
        mbsbEmail.processList()
        mbsbEmail.'list processing'()
        println('Hello World')
    }


}
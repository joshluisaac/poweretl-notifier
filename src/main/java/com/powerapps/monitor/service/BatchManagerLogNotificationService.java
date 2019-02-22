package com.powerapps.monitor.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import com.powerapps.monitor.batchmanager.BMStatus;
import com.powerapps.monitor.model.EmailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.notification.service.IEmailContentBuilder;
import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.model.BmProperties;
import com.powerapps.monitor.model.LogSummary;

@Service
public class BatchManagerLogNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(BatchManagerLogNotificationService.class);
    private final BatchManagerLogService logService;
    private final IEmailContentBuilder emailContentBuilder;
    private final IEmailClient emailClient;
    private final EmailConfigEntity emailConfig;

    @Value("${app.emailMaxQueueSize}")
    private String emailMaxQueueSize;

    @Value("${app.bm.emailTitle}")
    private String emailTitle;

    @Value("${app.mbsb.bm.itoperator}")
    private String itOperator;

    // emailMaxQueueSize
    @Autowired
    public BatchManagerLogNotificationService(BatchManagerLogService logService, IEmailContentBuilder emailContentBuilder,
                                              IEmailClient emailClient, EmailConfigEntity emailConfig) {
        this.logService = logService;
        this.emailContentBuilder = emailContentBuilder;
        this.emailClient = emailClient;
        this.emailConfig = emailConfig;
    }

    public void execute(final String recipient) throws IOException {
        final List<LogSummary> summaries = logService.getBmLogSummaries();
        final BmProperties bmConfig = logService.bmConfig;
        if (summaries.size() == 0) return;

        String title = null;
        int queueMaxSize = Integer.parseInt(emailMaxQueueSize);
            LOG.info("Processing {} of {} total Batch Manager logs", queueMaxSize, summaries.size());
            LOG.info("Queue max size: {}", queueMaxSize);
            for (int i = 0; i < queueMaxSize; i++) {
                boolean sendToOperator = false;
                final LogSummary summary = summaries.get(i);
                final int batchStatusId = summary.getBatchStatus();
                final String batchLogName = summary.getLogFileName();
                LOG.info("Processing: {}", batchLogName);
                final File file = new File(bmConfig.getBmRootPath() + "/", batchLogName);
                final String emailContent = emailContentBuilder.buildEmailTemplate("fragments/template_bm_email", summary);
                if (batchStatusId != BMStatus.PENDING.getStatusId()) {
                    title = formatEmailTitle(batchLogName,batchStatusId);
                    if (title.startsWith("SUCCESSFUL: MBSB Completion of PowerKollect Daily Batch loading for  Batch-daily-")) {
                        sendToOperator = true;
                    }
                    final String emailRecipient = (sendToOperator) ? itOperator : recipient;
                    Email mail = new EmailBuilder()
                            .from(emailConfig.getFromEmail())
                            .to(emailRecipient)
                            .body(emailContent)
                            .title(title)
                            .includeFileAtt(file)
                            .includeAtt(null)
                            .create();

                    /* Send email */
                    String emailStatus = emailClient.execute(mail);
                    cacheSuccessfulMail(emailStatus, bmConfig.getBmCache(), batchLogName);

                }
            }


    }


    private String formatEmailTitle(String batchLogName, int batchStatusId){
        String statusMsg = (batchStatusId == 1) ? "FAILED" : "SUCCESSFUL";
        return MessageFormat.format("{0}: {1} {2}", new Object[]{statusMsg, emailTitle, batchLogName});
    }


    private void cacheSuccessfulMail(String emailStatus, String cacheFilePath, String batchLogName) {
        if (emailStatus.equals("Success"))
            new FileUtils().writeTextFile(cacheFilePath, batchLogName + "\n");
    }

}

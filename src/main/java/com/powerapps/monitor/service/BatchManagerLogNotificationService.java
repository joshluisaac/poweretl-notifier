package com.powerapps.monitor.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

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
  public final BatchManagerLogService logService;
  private final IEmailContentBuilder emailContentBuilder;
  private final IEmailClient emailClient;
  private final EmailConfigEntity emailConfig;

  @Value("${app.emailMaxQueueSize}")
  String emailMaxQueueSize;

  @Value("${app.bm.emailTitle}")
  String emailTitle;

  @Value("${app.mbsb.bm.itoperator}")
  String itoperator;

  // emailMaxQueueSize
  @Autowired
  public BatchManagerLogNotificationService(BatchManagerLogService logService, IEmailContentBuilder emailContentBuilder,
                                            IEmailClient emailClient, EmailConfigEntity emailConfig) {
    this.logService = logService;
    this.emailContentBuilder = emailContentBuilder;
    this.emailClient = emailClient;
    this.emailConfig = emailConfig;
  }

  public void execute(String recipient) throws IOException {
    final String recipientTmp = recipient;
    List<LogSummary> summaries = logService.getBmLogSummaries();
    BmProperties bmConfig = logService.bmConfig;

    /* Build email content */
    String emailContent = null;
    String title = null;

    int queueMaxSize = Integer.parseInt(emailMaxQueueSize);
    LOG.info("Processing {} of {} total Batch Manager logs", queueMaxSize, summaries.size());
    LOG.info("Queue max size: {}", queueMaxSize);

    if (summaries.size() > 0) {
      for (int i = 0; i < queueMaxSize; i++) {
        LogSummary summary = summaries.get(i);
        int batchStatus = summary.getBatchStatus();
        String batchLogName = summary.getLogFileName();
        LOG.info("Processing: {}", batchLogName);
        File file = new File(bmConfig.getBmRootPath() + "/", batchLogName);
        emailContent = emailContentBuilder.buildEmailTemplate("fragments/template_bm_email", summary);

        if (batchStatus != 3) {
          if (batchStatus == 1) {
            /* failed email title */
            title = MessageFormat.format("{0}: {1} {2}", new Object[]{"FAILED", emailTitle, batchLogName});

          } else {
            /* successful email title */
            title = MessageFormat.format("{0}: {1} {2}", new Object[]{"SUCCESSFUL", emailTitle, batchLogName});

            if (title.startsWith("SUCCESSFUL: MBSB Completion of PowerKollect Daily Batch loading for  Batch-daily-")) {
              recipient = itoperator;
            } else {
              recipient = recipientTmp;
            }
          }
          /* Construct and assemble email object */
          Email mail = new EmailBuilder()
                  .from(emailConfig.getFromEmail())
                  .to(recipient)
                  .body(emailContent)
                  .title(title)
                  .includeFileAtt(file)
                  .includeAtt(null)
                  .create();

          /* Send email */
          String emailStatus = emailClient.execute(mail);

          /* if email was sent then persist to cache */
          persistToCache(emailStatus, bmConfig.getBmCache(), batchLogName);

        }
      }
    }

  }


  private void persistToCache(String emailStatus, String cacheFilePath, String batchLogName) {
    if (emailStatus.equals("Success"))
      new FileUtils().writeTextFile(cacheFilePath, batchLogName + "\n");
  }

}

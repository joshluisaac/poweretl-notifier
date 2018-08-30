package com.powerapps.monitor.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

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
import com.powerapps.monitor.component.EmailHelper;
import com.powerapps.monitor.model.BmProperties;
import com.powerapps.monitor.model.LogSummary;



@Service
public class BatchManagerLogNotificationService {
  
  
  private static final Logger LOG = LoggerFactory.getLogger(BatchManagerLogNotificationService.class);
  
  private final BatchManagerLogService logService;
  private final IEmailContentBuilder emailContentBuilder;
  private final IEmailClient emailClient;
  private final EmailHelper emailHelper;
  private final EmailConfigEntity emailConfig;
  
  @Value("${spring.mail.properties.batch.autoupdate.recipients}")
  String recipient;
  
  @Autowired
  public BatchManagerLogNotificationService(BatchManagerLogService logService,IEmailContentBuilder emailContentBuilder,
      IEmailClient emailClient,EmailHelper emailHelper, EmailConfigEntity emailConfig) {
      this.logService = logService;
      this.emailContentBuilder = emailContentBuilder;
      this.emailClient = emailClient;
      this.emailHelper = emailHelper;
      this.emailConfig = emailConfig;
  }
  
  
  
  public void execute() throws IOException {
    List<LogSummary> summaries = logService.getBmLogSummaries();
    int count = summaries.size();
    BmProperties bmConfig = logService.bmConfig; 
    
    /*Build email content*/
    String emailContent = null;
    String title = null;

    int queueMaxSize = 5;
    LOG.info("Queue max size: {}", queueMaxSize);
    for(int i=0; i < queueMaxSize; i++) {
      LogSummary summary = summaries.get(i);
      int batchStatus = summary.getBatchStatus();
      String log = summary.getLogFileName();
      if (batchStatus != 3) {
          if (batchStatus == 1) {
            /*send failed email*/
            title = MessageFormat.format("{1}: MBSB PowerApps System Alert - Batch Manager Report Summary For {0}", new Object[] {log,"FAILED"});
            emailContent = emailContentBuilder.buildEmailTemplate("fragments/template_bm_email", summary);
            
            /*Construct and assemble email object*/
            Email mail = new Email(emailConfig.getFromEmail(), recipient, title,emailContent, null, null);
            /*Send email*/
            String emailStatus = emailClient.execute(mail);
            
            /*if email was sent then persist to cache*/
            if(emailStatus.equals("Success")) new FileUtils().writeTextFile(bmConfig.getBmCache(), log + "\n");
          } else {
            /*send successful email*/
            title = MessageFormat.format("{1}: MBSB PowerApps System Alert - Batch Manager Report Summary For {0}", new Object[] {log,"SUCCESSFUL"});
            emailContent = emailContentBuilder.buildEmailTemplate("fragments/template_bm_email", summary);
            
            /*Construct and assemble email object*/
            Email mail = new Email(emailConfig.getFromEmail(), recipient, title,emailContent, null, null);
            /*Send email*/
            String emailStatus = emailClient.execute(mail);
            
            /*if email was sent then persist to cache*/
            if(emailStatus.equals("Success")) new FileUtils().writeTextFile(bmConfig.getBmCache(), log + "\n");
          }
      }
  }
} 

}

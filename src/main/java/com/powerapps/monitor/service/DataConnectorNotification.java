package com.powerapps.monitor.service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.notification.service.IEmailContentBuilder;
import com.kollect.etl.util.DateUtils;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.dataconnector.TotalLoaded;
import com.powerapps.monitor.component.DcStatistics;
import com.powerapps.monitor.component.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@ComponentScan({"org.springframework.mail.javamail","com.kollect.etl.notification.config","com.kollect.etl.notification.service","com.kollect.etl.notification.entity"})
public class DataConnectorNotification {

  @Value("${app.daysAgo}")
  private String daysAgo;
  private DcStatistics dcStats;
  
  private final IEmailContentBuilder emailContentBuilder;
  private final IEmailClient emailClient;
  private EmailHelper emailHelper;
  
  
  @Value("${app.extractionEmailLogPath}")
  private String extractionEmailLogPath;
  @Value("${app.cacheFilePath}")
  private String cacheFilePath;
  private String fromEmail = "datareceived@kollect.my";
  @Value("${spring.mail.properties.batch.autoupdate.recipients}")
  String recipient;

  @Autowired
  public DataConnectorNotification(
      DcStatistics dcStats,
      IEmailContentBuilder emailContentBuilder,
      IEmailClient emailClient,EmailHelper emailHelper) {
    this.dcStats = dcStats;
    this.emailContentBuilder = emailContentBuilder;
    this.emailClient = emailClient;
    this.emailHelper = emailHelper;
  }

  public void execute(String title, String serverLogPath, String context) throws IOException {
    
    String lineStartsWith = new DateUtils().getDaysAgoToString("yyyy-MM-dd", Integer.parseInt(daysAgo), new Date());
    String fileName = "dc_stats_"+ context +"_"+ lineStartsWith + ".json";
    List<String> cacheList = new FileUtils().readFile(cacheFilePath);
    boolean isExists = cacheList.contains(fileName);
    
    if(!isExists) {
      /*Serialize to JSON string*/
      List<TotalLoaded> stats = dcStats.getStats(serverLogPath, daysAgo);
      
      dcStats.jsonEncode(stats);
      /*Build email content*/
      String emailContent = emailContentBuilder.buildExtractLoadEmail("fragments/template_dc_email", stats);
      /*Construct and assemble email object*/
      Email mail = new Email(fromEmail, recipient, title,
              emailContent, null, null);
      /*Send email*/
      emailClient.execute(mail);
      /*write difference to cache*/
      emailHelper.persistToCache(fileName);
    }
    
    

  }
}

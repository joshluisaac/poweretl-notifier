package com.powerapps.monitor.service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.notification.service.IEmailContentBuilder;
import com.kollect.etl.util.DateUtils;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.dataconnector.TotalLoaded;
import com.powerapps.monitor.component.DcStatistics;
import com.powerapps.monitor.component.EmailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@ComponentScan({"org.springframework.mail.javamail","com.kollect.etl.notification.config","com.kollect.etl.notification.service","com.kollect.etl.notification.entity"})
public class DataConnectorNotification {


  private DcStatistics dcStats;
  private String fromEmail = "datareceived@kollect.my";
  
  private final IEmailContentBuilder emailContentBuilder;
  private final IEmailClient emailClient;
  private final EmailHelper emailHelper;
  private final Logger logger = LoggerFactory.getLogger(DataConnectorNotification.class);
  
  
  @Value("${app.daysAgo}")
  private String daysAgo;
  
  @Value("${app.extractionEmailLogPath}")
  private String extractionEmailLogPath;
  
  @Value("${app.cacheFilePath}")
  private String cacheFilePath;
  
  @Value("${spring.mail.properties.batch.autoupdate.recipients}")
  String recipient;
  
  @Value("${app.dcnotification.renotify}") String renotify;
  
  
  List<String> x = new ArrayList<>(Arrays.asList("true","false"));

  @Autowired
  public DataConnectorNotification(
      DcStatistics dcStats,
      IEmailContentBuilder emailContentBuilder,
      IEmailClient emailClient,EmailHelper emailHelper) {
    this.dcStats = dcStats;
    this.emailContentBuilder = emailContentBuilder;
    this.emailClient = emailClient;
    this.emailHelper = emailHelper;
    //validateProperty();
 
      
    
  }
  
  
  
  void validateProperty() {
  if(renotify.equals("true") || renotify.equals("false") ) {
  logger.error("The specified property 'app.dcnotification.renotify' is invalid. Please check propety value");
  throw new IllegalArgumentException("Invalid property 'app.dcnotification.renotify'");
}
  }

  public void execute(String title, String serverLogPath, String context) throws IOException {
    
    String lineStartsWith = new DateUtils().getDaysAgoToString("yyyy-MM-dd", Integer.parseInt(daysAgo), new Date());
    String fileName = "dc_stats_"+ context +"_"+ lineStartsWith + ".json";
    List<String> cacheList = new FileUtils().readFile(cacheFilePath);
    boolean isExists = cacheList.contains(fileName);
    
    boolean execute = (!isExists||(renotify.equals("true"))) ? true : false;
    
    if(execute) {
      
      logger.info("DataConnector Email Notification Running...at {} using thread {}", System.currentTimeMillis(), Thread.currentThread().getName());
      
      /*Serialize to JSON string*/
      List<TotalLoaded> stats = dcStats.getStats(serverLogPath, daysAgo);
      
      String jsonText = dcStats.jsonEncode(stats);
      System.out.println(jsonText);
      /*Build email content*/
      String emailContent = emailContentBuilder.buildExtractLoadEmail("fragments/template_dc_email", stats);
      /*Construct and assemble email object*/
      Email mail = new Email(fromEmail, recipient, title,emailContent, null, null);
              
      /*Send email*/
      emailClient.execute(mail);
      /*write difference to cache*/
      emailHelper.persistToCache(fileName);
    }
    
    

  }
}

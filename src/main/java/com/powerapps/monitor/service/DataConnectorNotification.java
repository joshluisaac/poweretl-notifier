package com.powerapps.monitor.service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.notification.service.IEmailContentBuilder;
import com.kollect.etl.util.CryptUtils;
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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ComponentScan({"org.springframework.mail.javamail","com.kollect.etl.notification.config","com.kollect.etl.notification.service","com.kollect.etl.notification.entity"})
public class DataConnectorNotification {


  private DcStatistics dcStats;
  private final IEmailContentBuilder emailContentBuilder;
  private final IEmailClient emailClient;
  private final EmailHelper emailHelper;
  private final EmailConfigEntity emailConfig;
  private FileUtils fileUtils = new FileUtils();
  private final Logger logger = LoggerFactory.getLogger(DataConnectorNotification.class);
  
  
  @Value("${app.daysAgo}")
  private String daysAgo;
  
  @Value("${app.extractionEmailLogPath}")
  private String extractionEmailLogPath;
  
  @Value("${app.outDir}")
  private String outDir;
  
  
  @Value("${app.cacheFilePath}")
  private String cacheFilePath;
  
  
  @Value("${app.dcnotification.renotify}") String renotify;
  

  @Autowired
  public DataConnectorNotification(
      DcStatistics dcStats,
      IEmailContentBuilder emailContentBuilder,
      IEmailClient emailClient,EmailHelper emailHelper, EmailConfigEntity emailConfig) {
    this.dcStats = dcStats;
    this.emailContentBuilder = emailContentBuilder;
    this.emailClient = emailClient;
    this.emailHelper = emailHelper;
    this.emailConfig = emailConfig;
  }


  public void execute(String title, String serverLogPath, String context, String recipient) throws IOException {
    boolean reexecute = false;
    String lineStartsWith = new DateUtils().getDaysAgoToString("yyyy-MM-dd", Integer.parseInt(daysAgo), new Date());
    String fileName = "dc_stats_"+ context +"_"+ lineStartsWith + ".json";
    List<String> cacheList = fileUtils.readFile(new File(cacheFilePath));
    boolean isExists = cacheList.contains(fileName);
    
    boolean execute = (!isExists||(renotify.equals("true"))) ? true : false;
    
    if(execute) {
      /*Serialize to JSON string*/
      List<TotalLoaded> stats = dcStats.getStats(serverLogPath, daysAgo);
      
      Map<String, Object> modelMap  = new HashMap<>();
      modelMap.put("stats", stats);
      modelMap.put("tenantContext", context);
      
      String jsonText = dcStats.jsonEncode(stats);
      String hashStr = CryptUtils.sha256HexHash(jsonText);
      String destFileName = outDir +"/"+ fileName;
      
      boolean fileExists = fileExistsChecker(fileName);
      
      if(fileExists) {
        String cachedFileContent = FileUtils.readFile(destFileName, StandardCharsets.UTF_8);
        String cachedFileContentHash = CryptUtils.sha256HexHash(cachedFileContent); 
        //check if current and previous are same
        reexecute = hashStr.equals(cachedFileContentHash) ? true : false;
      }
      
      if(!reexecute) {
        
        /*Build email content*/
        String emailContent = emailContentBuilder.buildEmailTemplate("fragments/template_dc_email", modelMap);
        /*Construct and assemble email object*/
        Email mail = new Email(emailConfig.getFromEmail(), recipient, title,emailContent, null, null);
        /*Send email*/
        String emailStatus = emailClient.execute(mail);
        if(emailStatus.equals("Success")) {
          deleteAndReplaceFile(destFileName, jsonText);
          /*write difference to cache*/
          emailHelper.persistToCache(fileName);
        }else {
          logger.info("Email Failed and wasn't sent");
        }
      } else {
        logger.info("Re-evaluating {} : DataConnector logs hasn't changed since last loading, email will not be resent", fileName);
      }
    }
  }
  
  private void deleteAndReplaceFile(String fileName, String content) {
    FileUtils futils = new FileUtils();
    futils.deleteFile(new File(fileName));
    futils.writeTextFile(fileName, content);
    logger.info("Replaced file {}", fileName);
  }
  
  
  private boolean fileExistsChecker(String fileName) {
    List<String> l=  new FileUtils().getFileList(new File(outDir));
    return l.contains(fileName) ? true:false;
  }
  
  
}
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
  private final  ZipFileHelper rejectionFetcher;
  private final IEmailClient emailClient;
  private final EmailHelper emailHelper;
  private final EmailConfigEntity emailConfig;
  private FileUtils fileUtils = new FileUtils();
  private final Logger logger = LoggerFactory.getLogger(DataConnectorNotification.class);
  
  
  @Value("${app.daysAgo}")
  private String daysAgo;
  
  @Value("${app.attachment.maxlimit}")
  private String emailFileSizeLimit;
  
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
      IEmailClient emailClient,EmailHelper emailHelper, EmailConfigEntity emailConfig,
      ZipFileHelper rejectionFetcher) {
    this.dcStats = dcStats;
    this.emailContentBuilder = emailContentBuilder;
    this.emailClient = emailClient;
    this.emailHelper = emailHelper;
    this.emailConfig = emailConfig;
    this.rejectionFetcher = rejectionFetcher;
  }
  
  public File getZipFile(String serverLogDir, String context) throws Exception {
    File[] files = rejectionFetcher.listChildFiles(new File(serverLogDir));
    String zipFile = rejectionFetcher.zipFile(context, files, serverLogDir);
    return new File(zipFile);
  }
  
  public void execute(String title, String serverLogPath, String context, String recipient) throws Exception{
    this.execute(title, serverLogPath, context, recipient,null);
  }


  public void execute(String title, String serverLogPath, String context, String recipient, String serverLogDir) throws Exception {
    boolean reexecute = false;
    String lineStartsWith = new DateUtils().getDaysAgoToString("yyyy-MM-dd", Integer.parseInt(daysAgo), new Date());
    String fileName = "dc_stats_"+ context +"_"+ lineStartsWith + ".json";
    List<String> cacheList = fileUtils.readFile(new File(cacheFilePath));
    boolean isExists = cacheList.contains(fileName);
    
    boolean execute = (!isExists||(renotify.equals("true"))) ? true : false;
    
    if(execute) {
      /*Serialize to JSON string*/
      List<TotalLoaded> stats = dcStats.getStats(serverLogPath, daysAgo);
      
      for(TotalLoaded tot: stats) {
        tot.setPercentageLoaded();
        tot.setPercentageRejected();
      }
      
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
        File zipFile = getZipFile(serverLogDir,context);
        long actualFileSize = (zipFile.length()/1000)/1000;
        Email mail = email(actualFileSize, emailConfig.getFromEmail(), recipient, title, emailContent, null, zipFile);
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
  
  /**
   * Sends an email if the actual file size is less than or equal to the defined limit.
   * @param actualFileSize of the sent attachment in MBs
   * @param from senders email
   * @param recipient recipient's email
   * @param title email subject
   * @param emailContent mail html document
   * @param attachment email multipart attachment
   * @param file email attachment
   * 
   * @return returns the mail object
   **/
  private Email email(long actualFileSize, String from, String recipient, String title,
      String emailContent, MultipartFile attachment,
      File file) {
    Email mail = null;
    if (actualFileSize <= Long.parseLong(emailFileSizeLimit)) {
     mail = new Email(from, recipient, title,emailContent, null, file);
    } else mail = new Email(from, recipient, title,emailContent, null, null);
    return mail;
  }
  
  /**
   * Deletes a file and replaces it with a new content 
   * 
   * @param fileName the name of the file to be deleted
   * @param content the new content
   *  
   **/
  private void deleteAndReplaceFile(String fileName, String content) {
    FileUtils futils = new FileUtils();
    futils.deleteFile(new File(fileName));
    futils.writeTextFile(fileName, content);
    logger.info("Deleted & replaced file {}", fileName);
  }
  
  /**
   * Checks if a file exits in a given directory.
   * 
   * @param fileName the name of the file to be be checked
   * @return returns true if the file exists and false if it doesn't
   **/
  private boolean fileExistsChecker(String fileName) {
    List<String> l=  new FileUtils().getFileList(new File(outDir));
    return l.contains(fileName) ? true:false;
  }
  
  
}
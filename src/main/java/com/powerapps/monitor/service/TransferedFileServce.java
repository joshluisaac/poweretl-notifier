package com.powerapps.monitor.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.notification.service.IEmailContentBuilder;
import com.kollect.etl.util.DateUtils;
import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.model.DataFile;

import ch.qos.logback.core.util.FileUtil;

@Service
public class TransferedFileServce {
  
  private final IEmailContentBuilder emailContentBuilder;
  private final IEmailClient emailClient;
  private final EmailConfigEntity emailConfig;
  
  @Autowired
  public TransferedFileServce(IEmailContentBuilder emailContentBuilder,
      IEmailClient emailClient,EmailConfigEntity emailConfig) {

      this.emailContentBuilder = emailContentBuilder;
      this.emailClient = emailClient;
      this.emailConfig = emailConfig;
  }
  
  @Value("${app.mbsb.dc.transfer.schemaFile}")
  String schemaFile;
  
  @Value("${app.mbsb.dc.transfer.stageDir}")
  String stagingDir;
  
  @Value("${app.mbsb.dc.transfer.emailTitle}")
  String transferEmailTitle;
  
  
  public void execute(String recipient, String context) throws IOException {
    Map<String, Object> modelMap  = new HashMap<>();
    modelMap.put("stats", getStats());
    modelMap.put("tenantContext", context);
    
    String emailContent = emailContentBuilder.buildEmailTemplate("fragments/template_dc_transfer_email", modelMap);
    
    /*Construct and assemble email object*/
    Email mail = new Email(emailConfig.getFromEmail(), recipient, transferEmailTitle,emailContent, null, null);
    /*Send email*/
    String emailStatus = emailClient.execute(mail);
  }
  
  
  List<String> readSchemaFile() throws IOException {
    //new FileUtils().re
    return new FileUtils().readFile(new FileUtils().getFileFromClasspath(schemaFile));
  }
  
  
  public File[] listChildFiles(final File dir, final String prefix) {
    File[] files = dir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.startsWith(prefix);
      }
    });
    return files;
  }
  
  
  List<DataFile> getStats() throws IOException {
    List<DataFile> dataFileStats = new ArrayList<>();
    String dateFormat  = new SimpleDateFormat("ddMMyyyy").format(new Date());
    List<String> schemaFileList = readSchemaFile();
    for(String schema : schemaFileList) {
      String prefix = schema.concat("_" + dateFormat);
      File[] files = listChildFiles(new File(stagingDir), prefix);
      if(files.length > 0) {
        for(File f : files) {
          long lineCount = Files.lines(Paths.get(stagingDir +"/"+ f.getName()),StandardCharsets.ISO_8859_1).count();
          
          int transferStatus = lineCount > 0 ? 1 : 2;
          
          String transferStatusDesc = transferStatus == 1 ? "Received" : "Received But Empty";
          
          DataFile dataFileStat = new DataFile(f.getName(), DateUtils.getTimestamp(f.lastModified()).toString(), (f.length()/1024)/1024, lineCount, 
              DateUtils.getTimestamp(f.lastModified()), transferStatus,transferStatusDesc);
          dataFileStats.add(dataFileStat);
          
        }
      } else {
        int transferStatus = 3;
        String transferStatusDesc = "Failed";
        DataFile dataFileStat = new DataFile(prefix.concat("TXT"), null, 0, 0, 
            DateUtils.getTimestamp(new Date().getTime()),transferStatus, transferStatusDesc);
        dataFileStats.add(dataFileStat);
      }

    }
    return dataFileStats;
  }
  
  
  
  public static void main(String[] args) throws Throwable {
    //new TransferedFileServce().getStats();
  }
  
  
  
  
  
  

}

package com.powerapps.monitor.service;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.powerapps.monitor.model.ServiceEngineErrorReport;
import com.powerapps.monitor.util.Utils;

@Service
public class ServiceEngineLogService {
  
  
  private static final Logger LOG = LoggerFactory.getLogger(ErrorFragmentIsolator.class);
  
  
  public String seExceptionRegex;
  
  public ServiceEngineLogService(@Value("${app.seExceptionRegex}") String seExceptionRegex) {
    this.seExceptionRegex = seExceptionRegex;
  }
  
 
  public List<ServiceEngineErrorReport> processLines(List<String> lines) {
    final List<ServiceEngineErrorReport> errorList = new ArrayList<>();
    for(String line : lines) {
      Matcher m = Utils.matcher(line, seExceptionRegex);
      boolean matches = m.find();
      if(matches) {
        String column[] = line.split("\\s+");
        ServiceEngineErrorReport report = new ServiceEngineErrorReport();
        report.setLineNumber(Integer.parseInt(column[0]));
        report.setEventQueryName(column[10]);
        report.setPcName(column[12]);
        String timeStamp = column[1] +" "+ column[2];
        report.setLogName("ServerError.log");
        report.setOccurredDate(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS").parseDateTime(timeStamp));
        errorList.add(report);
      }
    }
    return errorList;
  }
  
  public List<ServiceEngineErrorReport> execute(File f) {
    return processLines(Utils.readLogFile(f));
  }

}

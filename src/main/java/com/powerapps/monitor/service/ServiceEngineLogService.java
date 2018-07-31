package com.powerapps.monitor.service;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.powerapps.monitor.config.JsonReader;
import com.powerapps.monitor.model.SeProperties;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.powerapps.monitor.model.ServiceEngineErrorReport;
import com.powerapps.monitor.util.Utils;

@Service
public class ServiceEngineLogService {

  
  private JsonReader reader;
  private final Utils util;

  @Value("${app.seJson}")
  private String seJsonPath;

  @Autowired
  public ServiceEngineLogService(JsonReader reader, Utils util) {
    this.reader = reader;
    this.util = util;
  }
  
  
  private String getConfig() {
    return util.listToBuffer(util.readFile(new File(seJsonPath))).toString();
  }
  
  
  private String getRegexPattern(final String config){
    return reader.readJson(config, SeProperties.class).getSeExceptionRegex();
  }
  
  private String getRootPath(final String config){
    return reader.readJson(config, SeProperties.class).getSeRootPath();
  }
  
  private String getLogFileName(final String config){
    return reader.readJson(config, SeProperties.class).getSeErrorLog();
  }
  

  public List<ServiceEngineErrorReport> processLines(List<String> lines) {
    final String config = getConfig();
    final String regexPattern =  getRegexPattern(config);
    final String logFileName = getLogFileName(config);
    final List<ServiceEngineErrorReport> errorList = new ArrayList<>();
    for (String line : lines) {
      Matcher m = Utils.matcher(line, regexPattern);
      boolean matches = m.find();
      if (matches) {
        String column[] = line.split("\\s+");
        ServiceEngineErrorReport report = new ServiceEngineErrorReport();
        report.setLineNumber(Integer.parseInt(column[0]));
        report.setEventQueryName(column[10]);
        report.setPcName(column[12]);
        String timeStamp = column[1] + " " + column[2];
        report.setLogName(logFileName);
        report.setOccurredDate(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS").parseDateTime(timeStamp));
        errorList.add(report);
      }
    }
    return errorList;
  }

  public List<ServiceEngineErrorReport> execute() {
    final String config = getConfig();
    return processLines(Utils.readLogFile(new File(getRootPath(config), getLogFileName(config))));
  }
  
  public String getStackTrace(final int lineNumber) {
    final String config = getConfig();
    return getStackTrace(Utils.readLogFile(new File(getRootPath(config), getLogFileName(config))),lineNumber).toString();
  }
  
  
  //associates an error event to a multiline stacktrace
  private StringBuilder getStackTrace(List<String> lines, final int lineNumber) {
    final String config = getConfig();
    final String regexPattern =  getRegexPattern(config);
    final StringBuilder buf = new StringBuilder();
    for(int i=lineNumber; i<lines.size(); i++) {
      String line = lines.get(i);
      Matcher m = Utils.matcher(line, regexPattern);
      boolean matches = m.find();
      if(!matches) {
        buf.append(line+"\n");
      }else {
        break;
      }
    }
    return buf;
  }

}

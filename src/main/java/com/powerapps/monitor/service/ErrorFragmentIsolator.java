package com.powerapps.monitor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.powerapps.monitor.model.ServiceEngineErrorReport;

@Service
public class ErrorFragmentIsolator {
  
  private static final Logger LOG = LoggerFactory.getLogger(ErrorFragmentIsolator.class);
  public static final String REGEX = "^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (ERROR UNEXPECTED_EXCEPTION)";
  
  
  private Matcher matcher(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    return m;
  }

  public List<String> readLogFile(File f) {
    List<String> list = new ArrayList<>();
    int lineNumber = 0;
    try(BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
      String line;
      while((line = r.readLine()) != null) {
        lineNumber++;
        list.add(lineNumber  + " " + line);
      }
    } catch(Exception e) {
      LOG.error("{}",e.getMessage());
    }
    return list;
  }
  
  
  public List<ServiceEngineErrorReport> processLines(List<String> lines) {
    final List<ServiceEngineErrorReport> errorList = new ArrayList<>();
    for(String line : lines) {
      Matcher m = matcher(line, REGEX);
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
    return processLines(readLogFile(f));
  }
  
  
  public static void main(String args[]) {
    ErrorFragmentIsolator iso = new ErrorFragmentIsolator();
    //iso.readLogFile("/home/joshua/Desktop/HLBB/eCollect_batches_notifications/ServerError.log");
    List<ServiceEngineErrorReport> x = iso.execute(new File("/home/joshua/Desktop/HLBB/eCollect_batches_notifications/ServerError.log"));
    System.out.println(x.size());

  }

}

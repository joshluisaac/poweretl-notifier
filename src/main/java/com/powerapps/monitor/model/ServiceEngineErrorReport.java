package com.powerapps.monitor.model;

import org.joda.time.DateTime;

public class ServiceEngineErrorReport {
  
  int lineNumber;
  String pcName;
  String eventQueryName;
  DateTime occurredDate;
  String logName;
  
  
  public int getLineNumber() {
    return lineNumber;
  }
  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }
  public String getPcName() {
    return pcName;
  }
  public void setPcName(String pcName) {
    this.pcName = pcName;
  }
  public String getEventQueryName() {
    return eventQueryName;
  }
  public void setEventQueryName(String eventQueryName) {
    this.eventQueryName = eventQueryName;
  }

  public DateTime getOccurredDate() {
    return occurredDate;
  }

  public void setOccurredDate(DateTime occurredDate) {
    this.occurredDate = occurredDate;
  }

  public String getLogName() {
    return logName;
  }

  public void setLogName(String logName) {
    this.logName = logName;
  }
}

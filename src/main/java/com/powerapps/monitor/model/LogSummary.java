package com.powerapps.monitor.model;

import java.sql.Timestamp;

public class LogSummary {

  boolean isStartEntry, isDoneEntry, errorTerminated;
  Timestamp startTime, endTime;
  String logFileName;
  int batchStatus;

  public LogSummary(String logFileName, boolean isStartEntry, boolean isDoneEntry, boolean errorTerminated,
      Timestamp startTime, Timestamp endTime, int batchStatus) {
    this.isStartEntry = isStartEntry;
    this.isDoneEntry = isDoneEntry;
    this.errorTerminated = errorTerminated;
    this.startTime = startTime;
    this.endTime = endTime;
    this.logFileName = logFileName;
    this.batchStatus = batchStatus;
  }
  
  

  public int getBatchStatus() {
    return batchStatus;
  }



  public void setBatchStatus(int batchStatus) {
    this.batchStatus = batchStatus;
  }



  public boolean isErrorTerminated() {
    return errorTerminated;
  }

  public void setErrorTerminated(boolean errorTerminated) {
    this.errorTerminated = errorTerminated;
  }

  public String getLogFileName() {
    return logFileName;
  }

  public void setLogFileName(String logFileName) {
    this.logFileName = logFileName;
  }

  public boolean isStartEntry() {
    return isStartEntry;
  }

  public void setStartEntry(boolean isStartEntry) {
    this.isStartEntry = isStartEntry;
  }

  public boolean isDoneEntry() {
    return isDoneEntry;
  }

  public void setDoneEntry(boolean isDoneEntry) {
    this.isDoneEntry = isDoneEntry;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

}

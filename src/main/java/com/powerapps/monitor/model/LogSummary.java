package com.powerapps.monitor.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

public class LogSummary {

    boolean isStartEntry, isDoneEntry, errorTerminated;
    Timestamp startTime, endTime;
    String logFileName;
    int batchStatus;
    double runningTime;

    public LogSummary(){}

    public LogSummary(String logFileName, boolean isStartEntry, boolean isDoneEntry, boolean errorTerminated,
                      Timestamp startTime, Timestamp endTime, int batchStatus, double runningTime) {
        this.isStartEntry = isStartEntry;
        this.isDoneEntry = isDoneEntry;
        this.errorTerminated = errorTerminated;
        this.startTime = startTime;
        this.endTime = endTime;
        this.logFileName = logFileName;
        this.batchStatus = batchStatus;
        this.runningTime = runningTime;
    }


    public double getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(double runningTime) {
        this.runningTime = runningTime;
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

    @Override
    public String toString() {
        return "LogSummary{" + "\n" +
                "isStartEntry=" + isStartEntry + "\n" +
                ", isDoneEntry=" + isDoneEntry + "\n" +
                ", errorTerminated=" + errorTerminated + "\n" +
                ", startTime=" + startTime + "\n" +
                ", endTime=" + endTime + "\n" +
                ", logFileName='" + logFileName + '\'' + "\n" +
                ", batchStatus=" + batchStatus + "\n" +
                ", runningTime=" + runningTime + "\n" +
                '}';
    }
}

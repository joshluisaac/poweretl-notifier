package com.powerapps.monitor.model;

import java.sql.Timestamp;

public class Batch {
	Timestamp startTime, endTime;
	double runningTime;
	String batchName;
	public Batch(Timestamp startTime, Timestamp endTime, double runningTime, String batchName) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.runningTime = runningTime;
		this.batchName = batchName;
	}
	public Batch() {
		
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
	public double getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(double runningTime) {
		this.runningTime = runningTime;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	@Override
	public String toString() {
		return "Batch [startTime=" + startTime + ", endTime=" + endTime + ", runningTime=" + runningTime
				+ ", batchName=" + batchName + "]";
	}
	
	
	
	
}

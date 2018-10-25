package com.powerapps.monitor.model;

import java.sql.Timestamp;

public class DataFile {
  
  String fileName,ArrivalTime,transferStatusDesc;
  double numberOfBytes;
  long numberOfLines;
  Timestamp arrivalTimestamp;
  int transferStatus;
  
  
  
  

  public String getTransferStatusDesc() {
    return transferStatusDesc;
  }




  public void setTransferStatusDesc(String transferStatusDesc) {
    this.transferStatusDesc = transferStatusDesc;
  }




  public int getTransferStatus() {
    return transferStatus;
  }




  public void setTransferStatus(int transferStatus) {
    this.transferStatus = transferStatus;
  }




  public DataFile(String fileName, String arrivalTime, double numberOfBytes, long numberOfLines,
      Timestamp arrivalTimestamp, int transferStatus, String transferStatusDesc) {
    super();
    this.fileName = fileName;
    ArrivalTime = arrivalTime;
    this.numberOfBytes = numberOfBytes;
    this.numberOfLines = numberOfLines;
    this.arrivalTimestamp = arrivalTimestamp;
    this.transferStatus = transferStatus;
    this.transferStatusDesc = transferStatusDesc;
  }




  public DataFile(String fileName, String arrivalTime, double numberOfBytes, long numberOfLines,
      Timestamp arrivalTimestamp) {
    super();
    this.fileName = fileName;
    ArrivalTime = arrivalTime;
    this.numberOfBytes = numberOfBytes;
    this.numberOfLines = numberOfLines;
    this.arrivalTimestamp = arrivalTimestamp;
  }
  
  
  
  
  public Timestamp getArrivalTimestamp() {
    return arrivalTimestamp;
  }
  
  
  public void setArrivalTimestamp(Timestamp arrivalTimestamp) {
    this.arrivalTimestamp = arrivalTimestamp;
  }




  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public String getArrivalTime() {
    return ArrivalTime;
  }
  public void setArrivalTime(String arrivalTime) {
    ArrivalTime = arrivalTime;
  }
  public double getNumberOfBytes() {
    return numberOfBytes;
  }
  public void setNumberOfBytes(double numberOfBytes) {
    this.numberOfBytes = numberOfBytes;
  }
  public long getNumberOfLines() {
    return numberOfLines;
  }
  public void setNumberOfLines(long numberOfLines) {
    this.numberOfLines = numberOfLines;
  }
  @Override
  public String toString() {
    return "DataFile [fileName=" + fileName + ", ArrivalTime=" + ArrivalTime + ", numberOfBytes=" + numberOfBytes
        + ", numberOfLines=" + numberOfLines + "]";
  }
  
  

}

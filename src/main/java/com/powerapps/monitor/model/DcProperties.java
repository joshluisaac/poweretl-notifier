package com.powerapps.monitor.model;

public class DcProperties {

  private String dcRootPath, dcExceptionRegex, dcErrorLog;
  public DcProperties(String dcRootPath, String dcExceptionRegex, String dcErrorLog) {
    super();
    this.dcRootPath = dcRootPath;
    this.dcExceptionRegex = dcExceptionRegex;
    this.dcErrorLog = dcErrorLog;
  }
  public String getDcRootPath() {
    return dcRootPath;
  }
  public void setDcRootPath(String dcRootPath) {
    this.dcRootPath = dcRootPath;
  }
  public String getDcExceptionRegex() {
    return dcExceptionRegex;
  }
  public void setDcExceptionRegex(String dcExceptionRegex) {
    this.dcExceptionRegex = dcExceptionRegex;
  }
  public String getDcErrorLog() {
    return dcErrorLog;
  }
  public void setDcErrorLog(String dcErrorLog) {
    this.dcErrorLog = dcErrorLog;
  }

  public String toString() {
    return "DcProperties [dcRootPath=" + dcRootPath + ", dcExceptionRegex=" + dcExceptionRegex + ", dcErrorLog="
        + dcErrorLog + "]";
  }
  
  
  
}

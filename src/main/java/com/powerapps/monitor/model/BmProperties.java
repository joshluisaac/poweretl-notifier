package com.powerapps.monitor.model;




public class BmProperties {
  
  private String bmRootPath,batchStartRegex,batchDoneRegex,batchErrorRegex,bmCache;

  public BmProperties(){}
  
  public BmProperties(String bmRootPath, String batchStartRegex, String batchDoneRegex, String batchErrorRegex,
      String bmCache) {
    this.bmRootPath = bmRootPath;
    this.batchStartRegex = batchStartRegex;
    this.batchDoneRegex = batchDoneRegex;
    this.batchErrorRegex = batchErrorRegex;
    this.bmCache = bmCache;
  }
  

  public String getBmRootPath() {
    return bmRootPath;
  }

  public void setBmRootPath(String bmRootPath) {
    this.bmRootPath = bmRootPath;
  }

  public String getBatchStartRegex() {
    return batchStartRegex;
  }

  public void setBatchStartRegex(String batchStartRegex) {
    this.batchStartRegex = batchStartRegex;
  }

  public String getBatchDoneRegex() {
    return batchDoneRegex;
  }

  public void setBatchDoneRegex(String batchDoneRegex) {
    this.batchDoneRegex = batchDoneRegex;
  }

  public String getBatchErrorRegex() {
    return batchErrorRegex;
  }

  public void setBatchErrorRegex(String batchErrorRegex) {
    this.batchErrorRegex = batchErrorRegex;
  }

  public String getBmCache() {
    return bmCache;
  }

  public void setBmCache(String bmCache) {
    this.bmCache = bmCache;
  }


  @Override
  public String toString() {
    return "BmProperties [bmRootPath=" + bmRootPath + ", batchStartRegex=" + batchStartRegex + ", batchDoneRegex="
        + batchDoneRegex + ", batchErrorRegex=" + batchErrorRegex + ", bmCache=" + bmCache + "]";
  }


  
  

}

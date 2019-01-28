package com.powerapps.monitor.dataconnector;

public class DcEmailTenantConfiguration {

  String key;
  DcEmailConfiguration config;

  public DcEmailTenantConfiguration(String key, DcEmailConfiguration config) {
    this.key = key;
    this.config = config;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public DcEmailConfiguration getConfig() {
    return config;
  }

  public void setConfig(DcEmailConfiguration config) {
    this.config = config;
  }
}

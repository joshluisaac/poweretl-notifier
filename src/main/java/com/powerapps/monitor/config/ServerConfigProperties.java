package com.powerapps.monitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Maps external configuration property - server.properties to POJO object
 */

@Component
//@ConfigurationProperties("server")
public class ServerConfigProperties {
  
  private String port;
  

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "ServerProperties [port=" + port + "]";
  }
  
  
  public void getx() {
    
  }
  
  

}

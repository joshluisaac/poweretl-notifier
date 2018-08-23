package com.powerapps.monitor.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.dataconnector.Regex;

@Component
public class BeanInitializer {
 
  
  @Bean
  public Regex initializeRegex() {
    return new Regex();
  }
  
  
  @Bean
  public JsonUtils initializeJsonUtils() {
    return new JsonUtils();
  }
  
  
  
  
/*  @Bean
  public IType initializeBean() {
    return new IType() {
      
      @Override
      public void getType() {
        String x  = "Some shit happens";
        
      }
    };
  }*/

}

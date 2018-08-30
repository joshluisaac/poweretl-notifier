package com.powerapps.monitor.component;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.dataconnector.Regex;
import com.powerapps.monitor.model.BmProperties;

@Component
public class BeanInitializer {
  
  @Value("${app.bmJson}")
  private String bmJsonPath;
 
  
  @Bean
  public Regex initializeRegex() {
    return new Regex();
  }
  
  
  @Bean
  public JsonUtils initializeJsonUtils() {
    return new JsonUtils();
  }
  
  @Bean
  public FileUtils initializeFileUtils() {
    return new FileUtils();
  }
  
  
  
  @Bean
  private BmProperties getConfig(JsonUtils jsonUtils, FileUtils fileUtils) {
    BmProperties prop = null;
    try {
      prop =  jsonUtils.fromJson(new FileReader(fileUtils.getFileFromClasspath(bmJsonPath)), BmProperties.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return prop;
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

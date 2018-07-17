package com.powerapps.monitor.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;


/**
 * The purpose of this class is to convert Java plain objects into JSON formatted string
 * @author joshua
 */

@Component
public class JsonWriter {
  
  @Autowired
  private Gson gson;
  
  
  public <T> String generateJson(T prop) {
    return gson.toJson(prop);
  }
  
  

}

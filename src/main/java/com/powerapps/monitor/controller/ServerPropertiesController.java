package com.powerapps.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.powerapps.monitor.config.AppConfigProperties;
import com.powerapps.monitor.config.ServerConfigProperties;

@Controller
public class ServerPropertiesController {
  
  public final ServerConfigProperties servProp;
  private final AppConfigProperties appConfig;
  
  @Autowired
  public ServerPropertiesController(ServerConfigProperties servProp,AppConfigProperties appConfig) {
    this.servProp = servProp;
    this.appConfig = appConfig;
  }
  
  @GetMapping("/properties")
  @ResponseBody
  public Object properties(Model model) {
   
    return servProp.toString();
  }
  
  @GetMapping("/applicationproperties")
  @ResponseBody
  public Object appProperties(Model model) {   
    return appConfig.toString();
  }

}

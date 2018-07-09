package com.powerapps.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SettingController {

	@RequestMapping(value = "/sesettings", method = RequestMethod.GET)
	  public String serviceEngineSettings(Model model) {
	    return "serviceEngineSettings";
	  }
	  
	  @RequestMapping(value = "/batchManager", method = RequestMethod.GET)
	  public String batchManagerSettings(Model model) {
	    return "batchManagerSettings";
	  }
	  
	  @RequestMapping(value = "/dcsettings", method = RequestMethod.GET)
	  public String dataConnectorSettings(Model model) {
	    return "dataConnectorSettings";
	  }
	
	  @RequestMapping("/email")
	  public String email() {
	     return "emailSettingsForm";
	  }
	  
	
	
	
}

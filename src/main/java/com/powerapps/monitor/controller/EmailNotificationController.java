package com.powerapps.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmailNotificationController {

	@RequestMapping(value = "/seemailnotifreport", method = RequestMethod.GET)
	  public String serviceEngineEmailNotifReport(Model model) {
	    return "serviceEngineEmailNotifReport";
	  }
	
	
	@RequestMapping(value = "/dcemailnotifreport", method = RequestMethod.GET)
	  public String dataConnetorEmailNotifReport(Model model) {
	    return "dataConnetorEmailNotifReport";
	  }
	
	@RequestMapping(value = "/bmemailnotifreport", method = RequestMethod.GET)
	  public String batchManagerEmailNotifReport(Model model) {
	    return "batchManagerEmailNotifReport";
	  }
}

package com.powerapps.monitor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EmailNotificationController {

	@RequestMapping(value = "/seemailnotifreport", method = RequestMethod.GET)
	  public String serviceEngineEmailNotifReport(Model model) {
	    return "serviceEngineEmailNotifReport";
	  }
	
	
	@RequestMapping(value = "/dcemailnotifreport", method = RequestMethod.GET)
	  public String dataConnetorEmailNotifReport(Model model,HttpServletRequest req, 
	            HttpServletResponse response) {
	  
	  response.setContentType("text/html");
	  response.addHeader("Content-Disposition", "attachment; filename=" + "");
	  //response.setHeader(name, value);
	    return "dataConnectorEmailNotifReport";
	  }
	
	@RequestMapping(value = "/bmemailnotifreport", method = RequestMethod.GET)
	  public String batchManagerEmailNotifReport(Model model) {
	    return "batchManagerEmailNotifReport";
	  }
}

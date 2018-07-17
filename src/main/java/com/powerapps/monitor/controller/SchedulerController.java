package com.powerapps.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SchedulerController {


	  @RequestMapping(value = "/fixedinterval", method = RequestMethod.GET)
	  public String fixedIntervalSettings(Model model) {
	    return "fixedIntervalSettings";
	  }
	  
	  @RequestMapping(value = "/emailscheduler", method = RequestMethod.GET)
	  public String emailScheduler(Model model) {
	    return "emailSchedulerSettings";
	  }
	  
	
	
}

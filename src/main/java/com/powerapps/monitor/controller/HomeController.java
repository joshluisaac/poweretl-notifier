package com.powerapps.monitor.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.powerapps.monitor.service.ErrorLinesIsolatorService;

@Controller
public class HomeController {

  @Autowired
  ErrorLinesIsolatorService errorService;

  @RequestMapping("/")
  public String index() {
    return "redirect:/seerrorreport";
  }

  @RequestMapping(value = "/dcerrorreport", method = RequestMethod.GET)
  public String dataConnectorErrorLogReport(Model model) {
    final File logFile = new File("./eCollect_batches_notifications/ServerError.log");
    model.addAttribute("errorReport", errorService.execute(logFile));
    return "dash-errorlogreport";
  }

  @RequestMapping(value = "/bmerrorreport", method = RequestMethod.GET)
  public String batchManagerErrorLogReport(Model model) {
    final File logFile = new File("./eCollect_batches_notifications/ServerError.log");
    model.addAttribute("errorReport", errorService.execute(logFile));
    return "dash-errorlogreport";
  }

  @RequestMapping(value = "/seerrorreport", method = RequestMethod.GET)
  public String serviceEngineErrorLogReport(Model model) {
    final File logFile = new File("./eCollect_batches_notifications/ServerError.log");
    model.addAttribute("errorReport", errorService.execute(logFile));
    return "dash-errorlogreport";
  }

}

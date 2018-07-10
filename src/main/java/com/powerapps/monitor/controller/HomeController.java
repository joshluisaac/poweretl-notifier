package com.powerapps.monitor.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.powerapps.monitor.model.LogSummary;
import com.powerapps.monitor.service.BatchManagerLogService;
import com.powerapps.monitor.service.ServiceEngineLogService;

@Controller
public class HomeController {

  private ServiceEngineLogService errorService;
  private BatchManagerLogService bmService;
  private String bmRootPath;
  private String seRootPath;
  private String seErrorLog;

  @Autowired
  public HomeController(@Value("${app.bmRootPath}") String bmRootPath, @Value("${app.seRootPath}") String seRootPath,
      @Value("${app.seErrorLog}") String seErrorLog, ServiceEngineLogService errorService,
      BatchManagerLogService bmService) {
    this.errorService = errorService;
    this.bmService = bmService;
    this.bmRootPath = bmRootPath;
    this.seRootPath = seRootPath;
    this.seErrorLog = seErrorLog;
  }

  @RequestMapping("/")
  public String index() {
    return "redirect:/seerrorreport";
  }

  @RequestMapping(value = "/seerrorreport", method = RequestMethod.GET)
  public String serviceEngineErrorLogReport(Model model) {
    final File logFile = new File(seRootPath, seErrorLog);
    model.addAttribute("errorReport", errorService.execute(logFile));
    return "dash-errorlogreport";
  }

  @RequestMapping(value = "/seerrorreportJSON", method = RequestMethod.GET)
  @ResponseBody
  public Object serviceEngineErrorLogReportJSON(Model model) {
    return errorService.execute(new File(seRootPath, seErrorLog));
  }

  @RequestMapping(value = "/dcerrorreport", method = RequestMethod.GET)
  public String dataConnectorErrorLogReport(Model model) {
    final File logFile = new File(seRootPath, seErrorLog);
    model.addAttribute("errorReport", errorService.execute(logFile));
    return "dash-errorlogreport";
  }

  // return json response
  @RequestMapping(value = "/bmerrorreport", method = RequestMethod.GET)
  @ResponseBody
  public Object batchManagerErrorLogReport(Model model) {
    List<LogSummary> summary = bmService.processLogSummary(bmService.getLogFiles(new File(bmRootPath)));
    model.addAttribute("summaryList", summary);
    return summary;
  }

}

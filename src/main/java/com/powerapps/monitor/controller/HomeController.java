package com.powerapps.monitor.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.powerapps.monitor.model.LogSummary;
import com.powerapps.monitor.service.BatchManagerLogMetrics;
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
  @RequestMapping(value = "/bmerrorreportJSON", method = RequestMethod.GET)
  @ResponseBody
  public Object batchManagerErrorLogReportJSON(Model model) {
    List<LogSummary> summary = bmService.getAllLogSummary(bmService.getLogFiles(new File(bmRootPath)));
    model.addAttribute("summaryList", summary);
    return summary;
  }

  @RequestMapping(value = "/bmerrorreport", method = RequestMethod.GET)
   public Object batchManagerErrorLogReport(Model model) {
    List<LogSummary> summary = bmService.getAllLogSummary(bmService.getLogFiles(new File(bmRootPath)));
    model.addAttribute("summaryList", summary);
    return "batchManager-report";
  }
  
  @RequestMapping(value = "/downloadbatch", produces = "text/plain")
  public int downloadBatch(Model model, @RequestParam String logname, 
            HttpServletResponse response) throws IOException {
   
    //resource path
    Path path = Paths.get(bmRootPath+"/"+logname);
  
  response.setContentType("text/plain");
  response.addHeader("Content-Disposition", "attachment; filename=" + logname);
  
  ServletOutputStream outStream = response.getOutputStream();
  long numberOfBytesCopied = Files.copy(path, outStream);
  outStream.flush();
  System.out.println(numberOfBytesCopied);
  //response.setHeader(name, value);
    return -1;
  }
  
  @RequestMapping ("/batchdetailsJSON")
  @ResponseBody 
  public Object getBatchDetails() throws IOException {
	  File f = new File("C:\\Users\\Chelsea\\workspace\\powerappslogmonitor\\batches_notifications\\BatchManager\\eCollect\\Batch-01.Jun.2018-07_48_02.log");  
	  BatchManagerLogMetrics m = new BatchManagerLogMetrics();
	List<String> lines = m.getFile(f);	
	return m.extractFeatures(lines);
	
  }
  
  
}

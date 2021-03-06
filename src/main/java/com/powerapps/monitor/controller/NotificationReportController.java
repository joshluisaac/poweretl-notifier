package com.powerapps.monitor.controller;

import com.kollect.etl.util.CsvReadWriteUtils;
import com.kollect.etl.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NotificationReportController {
  @Value("${app.seAutoEmailLog}")
  private String seAutoEmailLog;
  @Value("${app.dcAutoEmailLog}")
  private String dcAutoEmailLog;
  @Value("${app.bmAutoEmailLog}")
  private String bmAutoEmailLog;
  @Value("${app.adhocEmailLog}")
  private String adhocEmailLog;

  private FileUtils fileUtils = new FileUtils();
  private final CsvReadWriteUtils csvReadWriteUtils = new CsvReadWriteUtils(new com.kollect.etl.util.Utils());

  @RequestMapping(value = "/seemailnotifreport", method = RequestMethod.GET)
  public String serviceEngineEmailNotifReport(Model model) {
    model.addAttribute("resultList", csvReadWriteUtils.readCsvToListMap(
            fileUtils.getFileFromClasspath(seAutoEmailLog).toString()));
    return "serviceEngineEmailNotifReport";
  }

  @RequestMapping(value = "/dcemailnotifreport", method = RequestMethod.GET)
  public String dataConnectorEmailNotifReport(Model model) {
    model.addAttribute("resultList", csvReadWriteUtils.readCsvToListMap(
            fileUtils.getFileFromClasspath(dcAutoEmailLog).toString()));
    return "dataConnectorEmailNotifReport";
  }

  @RequestMapping(value = "/bmemailnotifreport", method = RequestMethod.GET)
  public String batchManagerEmailNotifReport(Model model) {
    model.addAttribute("resultList", csvReadWriteUtils.readCsvToListMap(
            fileUtils.getFileFromClasspath(bmAutoEmailLog).toString()));
    return "batchManagerEmailNotifReport";
  }

  @GetMapping("/adhocemailnotifreport")
  public String adhocEmailNotifReport(Model model) {
    model.addAttribute("resultList", csvReadWriteUtils.readCsvToListMap(
            fileUtils.getFileFromClasspath(adhocEmailLog).toString()));
    return "adhocEmailNotifReport";
  }

}

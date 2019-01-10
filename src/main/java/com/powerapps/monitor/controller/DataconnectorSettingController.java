package com.powerapps.monitor.controller;

import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.config.JsonReader;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.DcProperties;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataconnectorSettingController {
  private final JsonWriter dcWriter;
  private final JsonReader jsonReader;
  private final Utils util;
  private FileUtils fileUtils = new FileUtils();

  @Value("${app.dcJson}")
  private String dcJsonPath;

  public DataconnectorSettingController(JsonWriter dcWriter, JsonReader jsonReader, Utils util) {
    super();
    this.dcWriter = dcWriter;
    this.jsonReader = jsonReader;
    this.util = util;
  }


  @RequestMapping(value = "/savedcsettings", method = RequestMethod.POST)
  public void saveDcSettings(@RequestParam String dcRootPath, @RequestParam String dcExceptionRegex, @RequestParam String dcErrorLog) {
    DcProperties dcProp = new DcProperties(dcRootPath, dcExceptionRegex, dcErrorLog);
    String output = dcWriter.generateJson(dcProp);
    util.writeTextFile(fileUtils.getFileFromClasspath(dcJsonPath).toString(), output, false);

  }

  @RequestMapping(value = "/viewdcsettings", method = RequestMethod.GET)
  public String viewDcSettings(Model model) {
    String JsonText = util.listToBuffer(util.readFile(fileUtils.getFileFromClasspath(dcJsonPath))).toString();
    model.addAttribute("result", jsonReader.readJson(JsonText, DcProperties.class));
    return "dataConnectorSettings";
  }

}

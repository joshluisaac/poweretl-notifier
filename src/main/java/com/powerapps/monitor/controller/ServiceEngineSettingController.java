package com.powerapps.monitor.controller;

import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.config.JsonReader;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.SeProperties;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServiceEngineSettingController {
  private final JsonWriter seWriter;
  private final JsonReader jsonReader;
  private final Utils util;
  private FileUtils fileUtils = new FileUtils();

  @Value("${app.seJson}")
  private String seJsonPath;

  @Autowired
  public ServiceEngineSettingController(JsonWriter seWriter, JsonReader jsonReader, Utils util) {
    this.seWriter = seWriter;
    this.jsonReader = jsonReader;
    this.util = util;
  }

  @RequestMapping(value = "/savesesetting", method = RequestMethod.POST)
  @ResponseBody
  public void save(@RequestParam String seRootPath, @RequestParam String seExceptionRegex,
      @RequestParam String seErrorLog) {
    SeProperties data = new SeProperties(seRootPath, seExceptionRegex, seErrorLog);
    String output = seWriter.generateJson(data);
    util.writeTextFile(
            fileUtils.getFileFromClasspath(seJsonPath).toString(), output, false);
  }

  @RequestMapping(value = "/viewsesetting", method = RequestMethod.GET)
  public String view(Model model) {
    String jsonText = util.listToBuffer(util.readFile(fileUtils.getFileFromClasspath(
            seJsonPath))).toString();
    model.addAttribute("result",jsonReader.readJson(jsonText,SeProperties.class));
    return "serviceEngineSettings";
  }

}

package com.powerapps.monitor.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonIOException;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.BmProperties;

@Controller
public class BatchManagerSettingController {
  
  
  private static final Logger LOG = LoggerFactory.getLogger(BatchManagerSettingController.class);
  private final JsonWriter bmWriter;
  
  @Autowired
  BatchManagerSettingController(JsonWriter bmWriter){
    this.bmWriter = bmWriter;
  }
  
  @RequestMapping(value = "/savebmsetting", method = RequestMethod.POST)
  @ResponseBody
  public String save(Model model,@RequestParam Integer lastLineProcessed,
      @RequestParam String batchErrorRegex,
      @RequestParam String batchDoneRegex,
      @RequestParam String batchStartRegex,
      @RequestParam String logFileName,
      @RequestParam String logFileLocation) throws JsonIOException, IOException {

    BmProperties bmProp = new BmProperties(logFileLocation, batchStartRegex, batchDoneRegex, batchErrorRegex, "x.txt");
    String out = bmWriter.generateJson(bmProp);
    return "none";
  }
  
  

}

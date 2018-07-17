package com.powerapps.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.SeProperties;
import com.powerapps.monitor.util.Utils;

@Controller
public class ServiceEngineSettingController {
	private final JsonWriter seWriter;
	  private final Utils util;
	  
	 @Autowired
	public ServiceEngineSettingController(JsonWriter seWriter, Utils util) {
		this.seWriter = seWriter;
		this.util = util;
	}
	 @RequestMapping(value="/savesesetting", method=RequestMethod.POST)
	 @ResponseBody
	 public String save(@RequestParam String seRootPath,
			 @RequestParam String seExceptionRegex,@RequestParam String seErrorLog) {
		 //Persist to DTO(data transfer object)
		 SeProperties data = new SeProperties(seRootPath, seExceptionRegex, seErrorLog);
		 //JSON conversion
		 String output = seWriter.generateJson(data);
		 //write JSON to file
		 util.writeTextFile("./config/seconfig.json", output, false);
		 return output;
	 }
	 @RequestMapping(value="/viewsesettings", method=RequestMethod.GET)
	 public String view() {
		 Gson gson = new Gson();
		 
		 SeProperties prop = gson.fromJson("{\"seRootPath\":\"kewfk\",\"seExceptionRegex\":\"fken\",\"seErrorLog\":\"fkewn12367\"}", SeProperties.class);
		 System.out.println(prop.getSeErrorLog());
		 
		 return null;
	 }
	  
}

package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.JsonToHashMap;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SchedulerController {

    @Value("${app.fixedSchedulerJson}")
    private String fixedSchedulerJsonPath;

    private final JsonWriter writer;
    private final Utils util;
    private final JsonToHashMap jsonToHashMap;

    @Autowired
    public SchedulerController(JsonWriter writer,
                               Utils util,
                               JsonToHashMap jsonToHashMap){
        this.writer=writer;
        this.util=util;
        this.jsonToHashMap=jsonToHashMap;
    }

    @RequestMapping(value = "/fixedinterval", method = RequestMethod.GET)
    public String fixedIntervalSettings(Model model) {
        model.addAttribute("result", this.jsonToHashMap.toHmap(fixedSchedulerJsonPath));
        return "fixedIntervalSettings";
    }

    @ResponseBody
    @PostMapping("/fixedinterval")
    public void saveSetting(String interval) {
        Map<String, String> fixedSetting = new HashMap<>();
        fixedSetting.put("interval", interval);
        String jsonOut = writer.generateJson(fixedSetting);
        util.writeTextFile(fixedSchedulerJsonPath, jsonOut, false);
    }

    @RequestMapping(value = "/emailscheduler", method = RequestMethod.GET)
    public String emailScheduler(Model model) {
        return "emailSchedulerSettings";
    }

}

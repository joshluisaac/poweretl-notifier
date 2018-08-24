package com.powerapps.monitor.controller;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.PropertiesUtils;
import com.kollect.etl.util.PropertyToHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class SchedulerController {

    @Value("${app.fixedSchedulerProp}")
    private String fixedSchedulerPropPath;

    @Value("${app.emailSchedulerProp}")
    private String emailSchedulerPropPath;

    private FileUtils fileUtils = new FileUtils();
    private PropertyToHashMap propertyToHashMap = new PropertyToHashMap();

    @RequestMapping(value = "/fixedinterval", method = RequestMethod.GET)
    public String fixedIntervalSettings(Model model) throws IOException {
        model.addAttribute("result",
                new PropertiesUtils().loadPropertiesFile(
                        fileUtils.getFileFromClasspath(fixedSchedulerPropPath).toString())
                        .getProperty("interval"));
        return "fixedIntervalSettings";
    }

    @ResponseBody
    @PostMapping("/fixedinterval")
    public void saveFixedSetting(@RequestParam HashMap<String, String> keyValuePair)
            throws IOException {
        HashMap<String, String> toStore = new HashMap<>();
        toStore.put("interval", keyValuePair.get("interval"));
        this.propertyToHashMap.hashMapToProp(
                fileUtils.getFileFromClasspath(fixedSchedulerPropPath).toString(),
                toStore);
    }

    @RequestMapping(value = "/emailscheduler", method = RequestMethod.GET)
    public String emailScheduler(Model model) throws IOException {
        model.addAttribute("result",
                this.propertyToHashMap.propToHashMap(
                        fileUtils.getFileFromClasspath(emailSchedulerPropPath).toString()));
        return "emailSchedulerSettings";
    }

    @ResponseBody
    @PostMapping("/emailscheduler")
    public void saveEmailSetting(@RequestParam HashMap<String, String> keyValuePair)
            throws IOException {
        HashMap<String, String> toStore = new HashMap<>();
        toStore.put("second", keyValuePair.get("second"));
        toStore.put("minute", keyValuePair.get("minute"));
        toStore.put("hour", keyValuePair.get("hour"));
        toStore.put("day", keyValuePair.get("day"));
        toStore.put("month", keyValuePair.get("month"));
        toStore.put("year", keyValuePair.get("year"));
        toStore.put("emailSchedulerExpression", keyValuePair.get("second")+" ".
                concat(keyValuePair.get("minute"))+" ".concat(keyValuePair.get("hour"))+" ".
                concat(keyValuePair.get("day"))+" ".concat(keyValuePair.get("month")+" ".
                concat(keyValuePair.get("year"))));
        this.propertyToHashMap.hashMapToProp(
                fileUtils.getFileFromClasspath(emailSchedulerPropPath).toString(),
                toStore);
    }

}

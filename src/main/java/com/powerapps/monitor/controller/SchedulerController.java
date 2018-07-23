package com.powerapps.monitor.controller;

import com.kollect.etl.util.PropertiesUtils;
import com.powerapps.monitor.util.PropertyFileUtils;
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

    private PropertyFileUtils propertyFileUtils;

    public SchedulerController(PropertyFileUtils propertyFileUtils) {
        this.propertyFileUtils = propertyFileUtils;
    }

    @RequestMapping(value = "/fixedinterval", method = RequestMethod.GET)
    public String fixedIntervalSettings(Model model) throws IOException {
        model.addAttribute("result",
                new PropertiesUtils().loadPropertiesFile(fixedSchedulerPropPath)
                        .getProperty("interval"));
        return "fixedIntervalSettings";
    }

    @ResponseBody
    @PostMapping("/fixedinterval")
    public void saveFixedSetting(@RequestParam HashMap<String, String> keyValuePair) throws IOException {
        this.propertyFileUtils.hashMapToProp(fixedSchedulerPropPath, keyValuePair);
    }

    @RequestMapping(value = "/emailscheduler", method = RequestMethod.GET)
    public String emailScheduler(Model model) throws IOException {
        model.addAttribute("result",
                this.propertyFileUtils.propToHashMap(emailSchedulerPropPath));
        System.out.println(
                this.propertyFileUtils.propToHashMap(emailSchedulerPropPath));
        return "emailSchedulerSettings";
    }

    @ResponseBody
    @PostMapping("/emailscheduler")
    public void saveEmailSetting(@RequestParam HashMap<String, String> keyValuePair)
            throws IOException {
        this.propertyFileUtils.hashMapToProp(emailSchedulerPropPath, keyValuePair);
    }

}

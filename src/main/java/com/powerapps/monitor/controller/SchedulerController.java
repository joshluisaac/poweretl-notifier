package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.PropertyFileUtils;
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
                this.propertyFileUtils.readPropFile
                        (fixedSchedulerPropPath).getProperty("interval"));
        return "fixedIntervalSettings";
    }

    @ResponseBody
    @PostMapping("/fixedinterval")
    public void saveFixedSetting(@RequestParam String interval) throws IOException {
        this.propertyFileUtils.writePropFile
                (fixedSchedulerPropPath, "interval", interval);
    }

    @RequestMapping(value = "/emailscheduler", method = RequestMethod.GET)
    public String emailScheduler(Model model) throws IOException {
        model.addAttribute("result", this.propertyFileUtils.readManyPropFromFile(emailSchedulerPropPath));
        System.out.println(this.propertyFileUtils.readManyPropFromFile(emailSchedulerPropPath));
        return "emailSchedulerSettings";
    }

    @ResponseBody
    @PostMapping("/emailscheduler")
    public void saveEmailSetting(@RequestParam HashMap<String, String> keyValuePair)
            throws IOException {
        this.propertyFileUtils.writeManyPropToFile(emailSchedulerPropPath, keyValuePair);
    }

}

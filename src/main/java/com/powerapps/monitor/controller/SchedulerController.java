package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.PropertyFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public void saveFixedSetting(String interval) throws IOException {
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
    public void saveEmailSetting(String year, String month, String day,
                                 String hour, String minute, String second)
            throws IOException {
        HashMap<String, String> keyValuePair = new HashMap<>();
        keyValuePair.put("year", year);
        keyValuePair.put("month", month);
        keyValuePair.put("day", day);
        keyValuePair.put("hour", hour);
        keyValuePair.put("minute", minute);
        keyValuePair.put("second", second);
        this.propertyFileUtils.writeManyPropToFile(emailSchedulerPropPath, keyValuePair);
    }

}

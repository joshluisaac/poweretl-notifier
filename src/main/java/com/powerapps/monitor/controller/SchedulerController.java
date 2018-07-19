package com.powerapps.monitor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Controller
public class SchedulerController {

    @Value("${app.fixedSchedulerProp}")
    private String fixedSchedulerPropPath;

    @RequestMapping(value = "/fixedinterval", method = RequestMethod.GET)
    public String fixedIntervalSettings(Model model) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(fixedSchedulerPropPath));
        model.addAttribute("result", prop.getProperty("interval"));
        return "fixedIntervalSettings";
    }

    @ResponseBody
    @PostMapping("/fixedinterval")
    public void saveFixedSetting(String interval) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(fixedSchedulerPropPath));
        prop.remove("interval");
        prop.setProperty("interval", interval);
        prop.store(new FileOutputStream(fixedSchedulerPropPath), null);
    }

    @RequestMapping(value = "/emailscheduler", method = RequestMethod.GET)
    public String emailScheduler(Model model) {
        return "emailSchedulerSettings";
    }

}

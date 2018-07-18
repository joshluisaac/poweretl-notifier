package com.powerapps.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DataconnectorSettingController {

    @RequestMapping(value = "/dcsettings", method = RequestMethod.GET)
    public String dataConnectorSettings(Model model) {
        return "dataConnectorSettings";
    }

}

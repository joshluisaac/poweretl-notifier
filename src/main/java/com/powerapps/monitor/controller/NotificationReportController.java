package com.powerapps.monitor.controller;

import com.powerapps.monitor.util.JsonToHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NotificationReportController {
    @Value("${app.bmJson}")
    private String bmJsonPath;
    @Value("${app.seJson}")
    private String seJsonPath;

    private JsonToHashMap jsonToHashMap;

    @Autowired
    public NotificationReportController(JsonToHashMap jsonToHashMap){
        this.jsonToHashMap=jsonToHashMap;
    }

    @RequestMapping(value = "/seemailnotifreport", method = RequestMethod.GET)
    public String serviceEngineEmailNotifReport(Model model) {
        return "serviceEngineEmailNotifReport";
    }

    @RequestMapping(value = "/dcemailnotifreport", method = RequestMethod.GET)
    public String dataConnectorEmailNotifReport(Model model) {
        return "dataConnectorEmailNotifReport";
    }

    @RequestMapping(value = "/bmemailnotifreport", method = RequestMethod.GET)
    public String batchManagerEmailNotifReport(Model model) {
        return "batchManagerEmailNotifReport";
    }

}

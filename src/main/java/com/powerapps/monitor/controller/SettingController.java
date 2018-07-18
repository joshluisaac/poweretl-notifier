package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.service.JsonFileReader;
import com.powerapps.monitor.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SettingController {
    @Value("${app.autoEmailJson}")
    private String autoEmailJsonPath;
    @Value("${app.adhocEmailJson}")
    private String adhocEmailJsonPath;

    private static final Logger LOG = LoggerFactory.getLogger(SettingController.class);


    private final JsonWriter settingsWriter;
    private final Utils util;
    private final JsonFileReader jsonFileReader;

    public SettingController(JsonWriter settingsWriter,
                             Utils util,
                             JsonFileReader jsonFileReader) {
        this.settingsWriter = settingsWriter;
        this.util = util;
        this.jsonFileReader = jsonFileReader;
    }

    @RequestMapping(value = "/sesettings", method = RequestMethod.GET)
    public String serviceEngineSettings(Model model) {
        return "serviceEngineSettings";
    }

    @RequestMapping(value = "/batchManager", method = RequestMethod.GET)
    public String batchManagerSettings(Model model) {
        return "batchManagerSettings";
    }

    @RequestMapping(value = "/dcsettings", method = RequestMethod.GET)
    public String dataConnectorSettings(Model model) {
        return "dataConnectorSettings";
    }

    @RequestMapping("/autoemailsettings")
    public String autoEmailSettings(Model model) throws IOException {
        model.addAttribute("result", this.jsonFileReader.readJsonToMap(autoEmailJsonPath));
        return "autoEmailSettingsForm";
    }

    @GetMapping("/adhocemailsettings")
    public String adhocEmailSettings(Model model) throws IOException {
        model.addAttribute("result", this.jsonFileReader.readJsonToMap(adhocEmailJsonPath));
        return "adhocEmailSettingsForm";
    }

    @ResponseBody
    @PostMapping("/autoemailsettings")
    public void autoEmailSettings(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String fromEmail,
                                  @RequestParam String host,
                                  @RequestParam Integer port,
                                  @RequestParam String recipient,
                                  @RequestParam String subject,
                                  @RequestParam String message) {
        Map<String, Object> autoEmailSettings = new HashMap<>();
        autoEmailSettings.put("username", username);
        autoEmailSettings.put("password", password);
        autoEmailSettings.put("fromEmail", fromEmail);
        autoEmailSettings.put("host", host);
        autoEmailSettings.put("port", port);
        autoEmailSettings.put("recipient", recipient);
        autoEmailSettings.put("subject", subject);
        autoEmailSettings.put("message", message);
        String jsonOut = settingsWriter.generateJson(autoEmailSettings);
        util.writeTextFile(autoEmailJsonPath, jsonOut, false);
    }

    @ResponseBody
    @PostMapping("/adhocemailsettings")
    public void adhocEmailSettings(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String fromEmail,
                                   @RequestParam String host,
                                   @RequestParam Integer port,
                                   @RequestParam String recipient) {
        Map<String, Object> adhocEmailSettings = new HashMap<>();
        adhocEmailSettings.put("username", username);
        adhocEmailSettings.put("password", password);
        adhocEmailSettings.put("fromEmail", fromEmail);
        adhocEmailSettings.put("host", host);
        adhocEmailSettings.put("port", port);
        adhocEmailSettings.put("recipient", recipient);
        String jsonOut = settingsWriter.generateJson(adhocEmailSettings);
        util.writeTextFile(adhocEmailJsonPath, jsonOut, false);
    }
}

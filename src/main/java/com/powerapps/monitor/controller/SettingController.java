package com.powerapps.monitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
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


    private final JsonWriter emailSettingsWriter;
    private final Utils util;

    public SettingController(JsonWriter emailSettingsWriter,
                             Utils util) {
        this.emailSettingsWriter = emailSettingsWriter;
        this.util = util;
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
        try {
            HashMap currentSettings =
                    new ObjectMapper().readValue(new File(autoEmailJsonPath), HashMap.class);
            model.addAttribute("result", currentSettings);
        } catch (FileNotFoundException e) {
            LOG.info("First time visiting the page, content will be displayed after saving json data.");
        }
        return "autoEmailSettingsForm";
    }

    @GetMapping("/adhocemailsettings")
    public String adhocEmailSettings(Model model) throws IOException {
        try {
            HashMap currentSettings =
                    new ObjectMapper().readValue(new File(adhocEmailJsonPath), HashMap.class);
            model.addAttribute("result", currentSettings);
        } catch (FileNotFoundException e) {
            LOG.info("First time visiting the page, content will be displayed after saving json data.");
        }
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
        String jsonOut = emailSettingsWriter.generateJson(autoEmailSettings);
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
        String jsonOut = emailSettingsWriter.generateJson(adhocEmailSettings);
        util.writeTextFile(adhocEmailJsonPath, jsonOut, false);
    }
}

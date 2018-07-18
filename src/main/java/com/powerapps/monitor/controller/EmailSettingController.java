package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.JsonReader;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailSettingController {
    @Value("${app.autoEmailJson}")
    private String autoEmailJsonPath;
    @Value("${app.adhocEmailJson}")
    private String adhocEmailJsonPath;

    private final JsonWriter settingsWriter;
    private final Utils util;
    private final JsonReader jsonReader;

    public EmailSettingController(JsonWriter settingsWriter,
                                  Utils util,
                                  JsonReader jsonReader) {
        this.settingsWriter = settingsWriter;
        this.util = util;
        this.jsonReader = jsonReader;
    }

    @RequestMapping("/autoemailsettings")
    public String autoEmailSettings(Model model) {
        model.addAttribute("result", jsonReader.readJson(this.util.listToBuffer(util.readFile(new File(autoEmailJsonPath))).toString(),HashMap.class));
        return "autoEmailSettingsForm";
    }

    @GetMapping("/adhocemailsettings")
    public String adhocEmailSettings(Model model) {
        model.addAttribute("result", jsonReader.readJson(this.util.listToBuffer(util.readFile(new File(adhocEmailJsonPath))).toString(),HashMap.class));
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

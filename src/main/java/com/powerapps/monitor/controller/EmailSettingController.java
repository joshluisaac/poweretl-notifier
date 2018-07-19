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
    @Value("${app.generalEmailJson}")
    private String generalEmailJsonPath;

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

    @GetMapping("/generalemailsettings")
    public String generalEmailSettings(Model model) {
        model.addAttribute("result", jsonReader.readJson(this.util.listToBuffer(util.readFile(new File(generalEmailJsonPath))).toString(),HashMap.class));
        return "generalEmailSettingsForm";
    }

    @ResponseBody
    @PostMapping("/generalemailsettings")
    public void generalEmailSettings(@RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String fromEmail,
                                     @RequestParam String host,
                                     @RequestParam String port){
        Map<String, String> generalEmailSettings = new HashMap<>();
        generalEmailSettings.put("username", username);
        generalEmailSettings.put("password", password);
        generalEmailSettings.put("fromEmail", fromEmail);
        generalEmailSettings.put("host", host);
        generalEmailSettings.put("port", port);
        String jsonOut = settingsWriter.generateJson(generalEmailSettings);
        util.writeTextFile(generalEmailJsonPath, jsonOut, false);
    }

    @ResponseBody
    @PostMapping("/autoemailsettings")
    public void autoEmailSettings(@RequestParam String recipient,
                                  @RequestParam String subject,
                                  @RequestParam String message) {
        Map<String, String> autoEmailSettings = new HashMap<>();
        autoEmailSettings.put("recipient", recipient);
        autoEmailSettings.put("subject", subject);
        autoEmailSettings.put("message", message);
        String jsonOut = settingsWriter.generateJson(autoEmailSettings);
        util.writeTextFile(autoEmailJsonPath, jsonOut, false);
    }

    @ResponseBody
    @PostMapping("/adhocemailsettings")
    public void adhocEmailSettings(@RequestParam String recipient) {
        Map<String, String> adhocEmailSettings = new HashMap<>();
        adhocEmailSettings.put("recipient", recipient);
        String jsonOut = settingsWriter.generateJson(adhocEmailSettings);
        util.writeTextFile(adhocEmailJsonPath, jsonOut, false);
    }
}

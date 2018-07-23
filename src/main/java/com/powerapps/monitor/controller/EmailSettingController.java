package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.util.JsonToHashMap;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private final JsonWriter writer;
    private final Utils util;
    private final JsonToHashMap jsonToHashMap;

    public EmailSettingController(JsonWriter writer,
                                  Utils util,
                                  JsonToHashMap jsonToHashMap) {
        this.writer = writer;
        this.util = util;
        this.jsonToHashMap = jsonToHashMap;
    }

    @RequestMapping("/autoemailsettings")
    public String autoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMap(autoEmailJsonPath));
        return "autoEmailSettingsForm";
    }

    @GetMapping("/adhocemailsettings")
    public String adhocEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMap(adhocEmailJsonPath));
        return "adhocEmailSettingsForm";
    }

    @GetMapping("/generalemailsettings")
    public String generalEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMap(generalEmailJsonPath));
        return "generalEmailSettingsForm";
    }

    @ResponseBody
    @PostMapping("/generalemailsettings")
    public void generalEmailSettings(@RequestParam HashMap<String, String> generalEmailSettings) {
        Map<String, String> toStore = new HashMap<>();
        toStore.put("username", generalEmailSettings.get("username"));
        toStore.put("password", generalEmailSettings.get("password"));
        toStore.put("fromEmail", generalEmailSettings.get("fromEmail"));
        toStore.put("host", generalEmailSettings.get("host"));
        toStore.put("port", generalEmailSettings.get("port"));
        String jsonOut = writer.generateJson(toStore);
        util.writeTextFile(generalEmailJsonPath, jsonOut, false);
    }

    @ResponseBody
    @PostMapping("/autoemailsettings")
    public void autoEmailSettings(@RequestParam HashMap<String, String> autoEmailSettings) {
        Map<String, String> toStore = new HashMap<>();
        toStore.put("recipient", autoEmailSettings.get("recipient"));
        toStore.put("subject", autoEmailSettings.get("subject"));
        toStore.put("message", autoEmailSettings.get("message"));
        String jsonOut = writer.generateJson(toStore);
        util.writeTextFile(autoEmailJsonPath, jsonOut, false);
    }

    @ResponseBody
    @PostMapping("/adhocemailsettings")
    public void adhocEmailSettings(@RequestParam HashMap<String, String> adhocEmailSettings) {
        Map<String, String> toStore = new HashMap<>();
        toStore.put("recipient", adhocEmailSettings.get("recipient"));
        String jsonOut = writer.generateJson(toStore);
        util.writeTextFile(adhocEmailJsonPath, jsonOut, false);
    }
}

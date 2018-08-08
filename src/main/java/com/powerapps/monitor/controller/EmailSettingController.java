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
    @Value("${app.seAutoEmailJson}")
    private String seAutoEmailJsonPath;
    @Value("${app.dcAutoEmailJson}")
    private String dcAutoEmailJsonPath;
    @Value("${app.bmAutoEmailJson}")
    private String bmAutoEmailJsonPath;
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

    @GetMapping("/seautoemailsettings")
    public String seAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMap(seAutoEmailJsonPath));
        return "seAutoEmailSettingsForm";
    }

    @GetMapping("/dcautoemailsettings")
    public String dcAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMap(dcAutoEmailJsonPath));
        return "dcAutoEmailSettingsForm";
    }

    @GetMapping("/bmautoemailsettings")
    public String bmAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMap(bmAutoEmailJsonPath));
        return "bmAutoEmailSettingsForm";
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

    private void autoEmailSettingWriter(HashMap<String, String> AutoEmailSettings,
                                        String autoEmailJsonPath){
        Map<String, String> toStore = new HashMap<>();
        toStore.put("recipient", AutoEmailSettings.get("recipient"));
        toStore.put("subject", AutoEmailSettings.get("subject"));
        toStore.put("message", AutoEmailSettings.get("message"));
        String jsonOut = writer.generateJson(toStore);
        util.writeTextFile(autoEmailJsonPath, jsonOut, false);
    }

    @ResponseBody
    @PostMapping("/seautoemailsettings")
    public void seAutoEmailSettings(@RequestParam HashMap<String, String> seAutoEmailSettings) {
        autoEmailSettingWriter(seAutoEmailSettings, seAutoEmailJsonPath);
    }

    @ResponseBody
    @PostMapping("/dcautoemailsettings")
    public void dcAutoEmailSettings(@RequestParam HashMap<String, String> dcAutoEmailSettings) {
        autoEmailSettingWriter(dcAutoEmailSettings, dcAutoEmailJsonPath);
    }

    @ResponseBody
    @PostMapping("/bmautoemailsettings")
    public void bmAutoEmailSettings(@RequestParam HashMap<String, String> bmAutoEmailSettings) {
        autoEmailSettingWriter(bmAutoEmailSettings, bmAutoEmailJsonPath);
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

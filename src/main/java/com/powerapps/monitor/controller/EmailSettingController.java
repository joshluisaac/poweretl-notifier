package com.powerapps.monitor.controller;

import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private JsonUtils jsonUtils = new JsonUtils();
    private Utils utils = new Utils();
    private final JsonToHashMap jsonToHashMap = new JsonToHashMap(jsonUtils, utils);

    @GetMapping("/seautoemailsettings")
    public String seAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(seAutoEmailJsonPath));
        return "seAutoEmailSettingsForm";
    }

    @GetMapping("/dcautoemailsettings")
    public String dcAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(dcAutoEmailJsonPath));
        return "dcAutoEmailSettingsForm";
    }

    @GetMapping("/bmautoemailsettings")
    public String bmAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(bmAutoEmailJsonPath));
        System.out.println(model);
        return "bmAutoEmailSettingsForm";
    }

    @GetMapping("/adhocemailsettings")
    public String adhocEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(adhocEmailJsonPath));
        return "adhocEmailSettingsForm";
    }

    @GetMapping("/generalemailsettings")
    public String generalEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(generalEmailJsonPath));
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
        String jsonOut = jsonUtils.toJson(toStore);
        utils.writeTextFile(generalEmailJsonPath, jsonOut, false);
    }

    private void autoEmailSettingWriter(HashMap<String, String> AutoEmailSettings,
                                        String autoEmailJsonPath){
        Map<String, String> toStore = new HashMap<>();
        toStore.put("recipient", AutoEmailSettings.get("recipient"));
        toStore.put("subject", AutoEmailSettings.get("subject"));
        toStore.put("message", AutoEmailSettings.get("message"));
        toStore.put("pathToEmailLog", AutoEmailSettings.get("pathToEmailLog"));
        String jsonOut = jsonUtils.toJson(toStore);
        utils.writeTextFile(autoEmailJsonPath, jsonOut, false);
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
        String jsonOut = jsonUtils.toJson(toStore);
        utils.writeTextFile(adhocEmailJsonPath, jsonOut, false);
    }
}

package com.powerapps.monitor.controller;

import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.util.FileUtils;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private FileUtils fileUtils = new FileUtils();

    @GetMapping("/seautoemailsettings")
    public String seAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(seAutoEmailJsonPath).toString()));
        return "seAutoEmailSettingsForm";
    }

    @GetMapping("/dcautoemailsettings")
    public String dcAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(dcAutoEmailJsonPath).toString()));
        return "dcAutoEmailSettingsForm";
    }

    @GetMapping("/bmautoemailsettings")
    public String bmAutoEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(bmAutoEmailJsonPath).toString()));
        System.out.println(model);
        return "bmAutoEmailSettingsForm";
    }

    @GetMapping("/adhocemailsettings")
    public String adhocEmailSettings(Model model) {
        model.addAttribute("result", jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(adhocEmailJsonPath).toString()));
        return "adhocEmailSettingsForm";
    }

    @GetMapping("/generalemailsettings")
    public String generalEmailSettings(Model model) throws FileNotFoundException {
      
      EmailConfigEntity config  = new JsonUtils().fromJson(new FileReader(new FileUtils().getFileFromClasspath(generalEmailJsonPath)), EmailConfigEntity.class);
      
        model.addAttribute("result", config);
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
        
        toStore.put("smtpAuth", generalEmailSettings.get("smtpAuth"));
        toStore.put("startTls", generalEmailSettings.get("startTls"));
        toStore.put("debug", generalEmailSettings.get("debug"));
        toStore.put("transportProtocol", generalEmailSettings.get("transportProtocol"));
        
        
        String jsonOut = jsonUtils.toJson(toStore);
        utils.writeTextFile(fileUtils.getFileFromClasspath(
                generalEmailJsonPath).toString(), jsonOut, false);
    }

    private void autoEmailSettingWriter(HashMap<String, String> AutoEmailSettings,
                                        String autoEmailJsonPath){
        Map<String, String> toStore = new HashMap<>();
        toStore.put("recipient", AutoEmailSettings.get("recipient"));
        toStore.put("subject", AutoEmailSettings.get("subject"));
        toStore.put("message", AutoEmailSettings.get("message"));
        toStore.put("pathToEmailLog", AutoEmailSettings.get("pathToEmailLog"));
        String jsonOut = jsonUtils.toJson(toStore);
        utils.writeTextFile(fileUtils.getFileFromClasspath(
                autoEmailJsonPath).toString(), jsonOut, false);
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
        utils.writeTextFile(fileUtils.getFileFromClasspath(
                        adhocEmailJsonPath).toString(), jsonOut, false);
    }
}

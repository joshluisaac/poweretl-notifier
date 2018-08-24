package com.powerapps.monitor.controller;

import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.config.JsonReader;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.BmProperties;
import com.powerapps.monitor.util.Path;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BatchManagerSettingController {

    private final JsonWriter bmWriter;
    private final JsonReader jsonReader;
    private final Utils util;
    private FileUtils fileUtils = new FileUtils();

    @Value("${app.bmJson}")
    private String bmJsonPath;

    @Autowired
    BatchManagerSettingController(JsonWriter bmWriter, Utils util,
                                  JsonReader jsonReader) {
        this.bmWriter = bmWriter;
        this.util = util;
        this.jsonReader=jsonReader;
    }

    @RequestMapping(value = Path.Web.GET_BM_WEB_SETTING, method = RequestMethod.GET)
    public String batchManagerSettings(Model model) {
        String jsonText = util.listToBuffer(util.readFile(fileUtils.getFileFromClasspath(bmJsonPath))).toString();
        model.addAttribute("result",jsonReader.readJson(jsonText,BmProperties.class));
        return "batchManagerSettings";
    }

    @RequestMapping(value = Path.Web.POST_BM_WEB_SETTING, method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestParam String bmRootPath,
                       @RequestParam String batchStartRegex,
                       @RequestParam String batchDoneRegex,
                       @RequestParam String batchErrorRegex,
                       @RequestParam String bmCache) {

        BmProperties bmProp = new BmProperties(bmRootPath, batchStartRegex, batchDoneRegex, batchErrorRegex, bmCache);
        String out = bmWriter.generateJson(bmProp);
        util.writeTextFile(fileUtils.getFileFromClasspath(bmJsonPath).toString(), out, false);
    }


}

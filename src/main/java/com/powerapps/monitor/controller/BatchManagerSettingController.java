package com.powerapps.monitor.controller;

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

import java.io.File;

@Controller
public class BatchManagerSettingController {

    private final JsonWriter bmWriter;
    private final JsonReader jsonReader;
    private final Utils util;

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
        String jsonText = util.listToBuffer(util.readFile(new File(bmJsonPath))).toString();
        model.addAttribute("result",jsonReader.readJson(jsonText,BmProperties.class));
        return "batchManagerSettings";
    }

    @RequestMapping(value = Path.Web.POST_BM_WEB_SETTING, method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestParam String bmRootPath,
                       @RequestParam String batchStartRegex,
                       @RequestParam String batchDoneRegex,
                       @RequestParam String batchErrorRegex) {

        BmProperties bmProp = new BmProperties(bmRootPath, batchStartRegex, batchDoneRegex, batchErrorRegex, "x.txt");
        String out = bmWriter.generateJson(bmProp);
        util.writeTextFile(bmJsonPath, out, false);
    }


}

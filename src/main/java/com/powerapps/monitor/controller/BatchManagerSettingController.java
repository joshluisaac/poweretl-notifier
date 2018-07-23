package com.powerapps.monitor.controller;


import com.powerapps.monitor.util.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.BmProperties;
import com.powerapps.monitor.util.Utils;;

@Controller
public class BatchManagerSettingController {

    private static final Logger LOG = LoggerFactory.getLogger(BatchManagerSettingController.class);
    private final JsonWriter bmWriter;
    private final Utils util;

    @Value("${app.bmJson}")
    private String bmJsonPath;


    @Autowired
    BatchManagerSettingController(JsonWriter bmWriter, Utils util) {
        this.bmWriter = bmWriter;
        this.util = util;
    }

    @RequestMapping(value = Path.Web.GET_BM_WEB_SETTING, method = RequestMethod.GET)
    public String batchManagerSettings(Model model) {
        return "batchManagerSettings";
    }

    @RequestMapping(value = Path.Web.POST_BM_WEB_SETTING, method = RequestMethod.POST)
    @ResponseBody
    public String save(Model model, @RequestParam Integer lastLineProcessed,
                       @RequestParam String batchErrorRegex,
                       @RequestParam String batchDoneRegex,
                       @RequestParam String batchStartRegex,
                       @RequestParam String logFileName,
                       @RequestParam String logFileLocation) {

        BmProperties bmProp = new BmProperties(logFileLocation, batchStartRegex, batchDoneRegex, batchErrorRegex, "x.txt");
        String out = bmWriter.generateJson(bmProp);
        util.writeTextFile(bmJsonPath, out, false);
        return out;
    }


}

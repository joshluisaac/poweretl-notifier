package com.powerapps.monitor.controller;

import com.powerapps.monitor.config.JsonReader;
import com.powerapps.monitor.model.SeProperties;
import com.powerapps.monitor.service.EmailClient;
import com.powerapps.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

@Controller
public class EmailNotificationController {

    @Value("${app.bmRootPath}")
    private String bmRootPath;
    @Value("${app.seJson}")
    private String seJsonPath;

    private EmailClient emailClient;
    private JsonReader reader;
    private Utils util;

    @Autowired
    public EmailNotificationController(EmailClient emailClient,
                                       JsonReader reader,
                                       Utils util){
        this.emailClient=emailClient;
        this.reader=reader;
        this.util=util;
    }

    private String getConfig() {
        return util.listToBuffer(util.readFile(new File(seJsonPath))).toString();
    }

    private String getRootPath(final String config){
        return reader.readJson(config, SeProperties.class).getSeRootPath();
    }

    @RequestMapping(value = "/seemailnotifreport", method = RequestMethod.GET)
    public String serviceEngineEmailNotifReport(Model model) {
        return "serviceEngineEmailNotifReport";
    }

    @RequestMapping(value = "/dcemailnotifreport", method = RequestMethod.GET)
    public String dataConnectorEmailNotifReport(Model model) {
        return "dataConnectorEmailNotifReport";
    }

    @RequestMapping(value = "/bmemailnotifreport", method = RequestMethod.GET)
    public String batchManagerEmailNotifReport(Model model) {
        return "batchManagerEmailNotifReport";
    }

    @GetMapping("/sendadhocemail")
    public Object sendAdhocEmail(@RequestParam String logFileName,
                                 Model model){
        model.addAttribute("logFileName", logFileName);
        return "fragments/template_adhoc_email_modal";
    }

    @PostMapping("/sendadhocemail")
    public Object sendAdhocEmail(@RequestParam String logFileName,
                                 @RequestParam String title,
                                 @RequestParam String body,
                                 @RequestParam (required = false) MultipartFile attachment,
                                 RedirectAttributes redirAttr) {
        String redirectPage = null;
        /* Check if file is too large (>1MB), redirect to error message if true. */
        if (attachment.getSize() > 1000000){
            redirAttr.addFlashAttribute("size", "Error: Email attachment file is too large, please upload only up to 1MB.");
        }

        /* Check if attachment meets size criterion and is not empty, or if there was no attachment at all. */
        if (attachment.getSize() < 1000001 && attachment.getSize() != 0 || attachment.isEmpty()){
            if (logFileName.equals("ServerError.log")){
                File logFile = new File(this.getRootPath(this.getConfig())+"/"+logFileName);
                this.emailClient.sendAdhocEmail(title, body, attachment, logFile);
                redirectPage = "seerrorreport";
            }
            else {
                File logFile = new File(bmRootPath + "/" + logFileName);
                this.emailClient.sendAdhocEmail(title, body, attachment, logFile);
                redirectPage = "bmerrorreport";
            }
            redirAttr.addFlashAttribute("success", "Email for "+logFileName+" was sent successfully.");
        }
        return "redirect:/"+redirectPage;
    }
}

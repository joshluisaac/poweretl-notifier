package com.powerapps.monitor.controller;

import com.powerapps.monitor.service.EmailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class EmailNotificationController {
    @Autowired
    private EmailClient emailClient;
    @Value("${app.bmRootPath}")
    private String bmRootPath;

    private static final String UPLOAD_DIR = "uploads/";

    @RequestMapping(value = "/seemailnotifreport", method = RequestMethod.GET)
    public String serviceEngineEmailNotifReport(Model model) {
        return "serviceEngineEmailNotifReport";
    }


    @RequestMapping(value = "/dcemailnotifreport", method = RequestMethod.GET)
    public String dataConnetorEmailNotifReport(Model model) {
        return "dataConnectorEmailNotifReport";
    }

    @RequestMapping(value = "/bmemailnotifreport", method = RequestMethod.GET)
    public String batchManagerEmailNotifReport(Model model) {
        return "batchManagerEmailNotifReport";
    }

    @PostMapping("/sendadhocemail")
    public Object sendAdhocEmail(@RequestParam String title,
                                 @RequestParam String body,
                                 @RequestParam (required = false) MultipartFile attachment,
                                 Model model, @RequestParam String
                                 logFileName) {
        /* Check if file is too large (>1MB), redirect to error message if true. */
        if (attachment.getSize() > 1000000){
            model.addAttribute("size", "File size is too large, please upload only up to 1MB.");
        }
        /* Check if file is empty, redirect to error message if true. */
        if (attachment.isEmpty())
            model.addAttribute("content", "File is empty, please re-upload file.");
        if (attachment.getSize() < 1000001 && !attachment.isEmpty()){
            File logFile = new File(bmRootPath+"/"+logFileName);
            this.emailClient.sendAdhocEmail(title, body, attachment, logFile);
        }
        return "redirect:/bmerrorreport";
    }
}

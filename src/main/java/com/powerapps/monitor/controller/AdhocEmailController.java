package com.powerapps.monitor.controller;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import com.powerapps.monitor.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

@Controller
public class AdhocEmailController {
    @Value("${app.bmJson}")
    private String bmJsonPath;
    @Value("${app.seJson}")
    private String seJsonPath;
    private FileUtils fileUtils = new FileUtils();

    private EmailSenderService emailClient;
    private final JsonToHashMap jsonToHashMap = new JsonToHashMap(new JsonUtils(), new Utils());

    public AdhocEmailController(EmailSenderService emailClient){
        this.emailClient=emailClient;
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
                File logFile = fileUtils.getFileFromClasspath(jsonToHashMap.toHashMapFromJson(seJsonPath).get("seRootPath")
                        +"/"+logFileName);
                this.emailClient.sendAdhocEmail(title, body, attachment, logFile);
                redirectPage = "seerrorreport";
            }
            else {
                File logFile = fileUtils.getFileFromClasspath(jsonToHashMap.toHashMapFromJson(bmJsonPath).get("bmRootPath")
                        + "/" + logFileName);
                this.emailClient.sendAdhocEmail(title, body, attachment, logFile);
                redirectPage = "bmerrorreport";
            }
            redirAttr.addFlashAttribute("success", "Email for "+logFileName+" was sent successfully.");
        }
        return "redirect:/"+redirectPage;
    }
}

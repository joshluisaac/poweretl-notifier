package com.powerapps.monitor.service;

import com.kollect.etl.notification.service.EmailContentBuilder;
import com.kollect.etl.notification.service.EmailLogger;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.notification.service.IEmailLogger;
import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import java.io.File;

/**
 * This class is used to send out the emails, automated and adhoc.
 *
 * @author hashim
 */
@Service
public class EmailClient {
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
    private final String templateName = "fragments/template_email_template";

    private JavaMailSender mailSender;
    private IEmailLogger emailLogger = new EmailLogger();
    private final TemplateEngine templateEngine;
    private final JsonToHashMap jsonToHashMap = new JsonToHashMap(new JsonUtils(), new Utils());

    @Autowired
    public EmailClient(JavaMailSender mailSender,
                       TemplateEngine templateEngine){
        this.mailSender = mailSender;
        this.templateEngine=templateEngine;
    }

    public void sendAdhocEmail(String title, String body,
                               MultipartFile attachment, File logFile){
        final IEmailClient eClient =
                new com.kollect.etl.notification.service.EmailClient(mailSender,emailLogger);
        eClient.sendAdhocEmail(jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("fromEmail"),
                jsonToHashMap.toHashMapFromJson(adhocEmailJsonPath).get("recipient"),
                title, body, attachment, logFile,
                new EmailContentBuilder(templateEngine), templateName,
                "config/adhocEmailLog.csv");
    }

    private void sendAutoEmail(File logFile, String autoEmailJsonPath){
        final IEmailClient eClient =
                new com.kollect.etl.notification.service.EmailClient(mailSender, emailLogger);
        eClient.sendAutoEmail(jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("fromEmail"),
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("recipient"),
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("subject"),
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("message"),
                logFile, new EmailContentBuilder(templateEngine), templateName,
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("pathToEmailLog"));

    }

    private void sendSeAutoEmail(File logFile){
        sendAutoEmail(logFile, seAutoEmailJsonPath);
    }

    private void sendDcAutoEmail(File logFile){
        sendAutoEmail(logFile, dcAutoEmailJsonPath);
    }

    private void sendBmAutoEmail(File logFile){
        sendAutoEmail(logFile, bmAutoEmailJsonPath);
    }
}

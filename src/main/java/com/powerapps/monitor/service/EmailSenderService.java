package com.powerapps.monitor.service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.*;
import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * This class is used to send out the emails, automated and adhoc.
 *
 * @author hashim
 */
@Service
public class EmailSenderService {
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
    @Value("${app.adhocEmailLog}")
    String adhocEmailLogPath;

    private IEmailClient emailClient;
    private final IEmailContentBuilder emailContentBuilder;
    private IEmailLogger emailLogger = new EmailLogger();

    private final JsonToHashMap jsonToHashMap = new JsonToHashMap(new JsonUtils(), new Utils());

    @Autowired
    public EmailSenderService(IEmailClient emailClient,IEmailContentBuilder emailContentBuilder) {
        this.emailClient = emailClient;
        this.emailContentBuilder = emailContentBuilder;
    }

    private String getSendTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
        return sdf.format(new Date());
    }

    public void sendAdhocEmail(String title, String body,
                               MultipartFile attachment, File logFile) {
        String content = emailContentBuilder.buildSimpleEmail(body, templateName);
                
        Email mail = new Email(jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("fromEmail"),
                jsonToHashMap.toHashMapFromJson(adhocEmailJsonPath).get("recipient"),
                title, content, attachment, logFile);
        String status = emailClient.execute(mail);
        String[] logArray = {jsonToHashMap.toHashMapFromJson(adhocEmailJsonPath).get("recipient"),
                title, logFile.getName(), getSendTime(), status};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)),
                adhocEmailLogPath);
    }

    private void sendAutoEmail(File logFile, String autoEmailJsonPath) {
        String content = emailContentBuilder.buildSimpleEmail(jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("message")
                , templateName);
        Email mail = new Email(jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("fromEmail"),
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("recipient"),
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("subject"),
                content, null, logFile);
        String status = emailClient.execute(mail);
        String[] logArray = {jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("recipient")
                , jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("subject")
                , logFile.getName(), getSendTime(), status};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)),
                jsonToHashMap.toHashMapFromJson(autoEmailJsonPath).get("pathToEmailLog"));
    }

    private void sendSeAutoEmail(File logFile) {
        sendAutoEmail(logFile, seAutoEmailJsonPath);
    }

    private void sendDcAutoEmail(File logFile) {
        sendAutoEmail(logFile, dcAutoEmailJsonPath);
    }

    private void sendBmAutoEmail(File logFile) {
        sendAutoEmail(logFile, bmAutoEmailJsonPath);
    }
}

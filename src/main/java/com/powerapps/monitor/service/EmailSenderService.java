package com.powerapps.monitor.service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.*;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan({"org.springframework.mail.javamail","com.kollect.etl.notification.config","com.kollect.etl.notification.service","com.kollect.etl.notification.entity"})
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

    private FileUtils fileUtils = new FileUtils();
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
                
        Email mail = new Email(jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(generalEmailJsonPath).toString()).get("fromEmail"),
                jsonToHashMap.toHashMapFromJson(
                        fileUtils.getFileFromClasspath(adhocEmailJsonPath).toString())
                        .get("recipient"),
                title, content, attachment, logFile);
        String status = emailClient.execute(mail);
        String[] logArray = {jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(adhocEmailJsonPath).toString())
                .get("recipient"),
                title, logFile.getName(), getSendTime(), status};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)),
                fileUtils.getFileFromClasspath(adhocEmailLogPath).toString());
    }

    private void sendAutoEmail(File logFile, String autoEmailJsonPath) {
        String content = emailContentBuilder.buildSimpleEmail(jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(autoEmailJsonPath).toString())
                        .get("message")
                , templateName);
        Email mail = new Email(jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(generalEmailJsonPath).toString()).get("fromEmail"),
                jsonToHashMap.toHashMapFromJson(
                        fileUtils.getFileFromClasspath(autoEmailJsonPath).toString())
                        .get("recipient"),
                jsonToHashMap.toHashMapFromJson(
                        fileUtils.getFileFromClasspath(autoEmailJsonPath).toString())
                        .get("subject"),
                content, null, logFile);
        String status = emailClient.execute(mail);
        String[] logArray = {jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(autoEmailJsonPath).toString())
                .get("recipient")
                , jsonToHashMap.toHashMapFromJson(
                fileUtils.getFileFromClasspath(autoEmailJsonPath).toString())
                .get("subject")
                , logFile.getName(), getSendTime(), status};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)),
                jsonToHashMap.toHashMapFromJson(
                        fileUtils.getFileFromClasspath(autoEmailJsonPath).toString())
                        .get("pathToEmailLog"));
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

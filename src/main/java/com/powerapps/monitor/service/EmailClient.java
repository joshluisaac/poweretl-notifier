package com.powerapps.monitor.service;

import com.powerapps.monitor.util.JsonToHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * This class is used to send out the emails, automated and adhoc.
 *
 * @author hashim
 */
@Service
public class EmailClient {
    @Value("${app.autoEmailJson}")
    private String autoEmailJsonPath;
    @Value("${app.adhocEmailJson}")
    private String adhocEmailJsonPath;
    @Value("${app.generalEmailJson}")
    private String generalEmailJsonPath;

    private JavaMailSender mailSender;
    private MailContentBuilder builder;
    private final JsonToHashMap jsonToHashMap;

    private static final Logger LOG = LoggerFactory.getLogger(EmailClient.class);

    @Autowired
    public EmailClient(JavaMailSender mailSender,
                       MailContentBuilder builder,
                       JsonToHashMap jsonToHashMap){
        this.mailSender = mailSender;
        this.builder = builder;
        this.jsonToHashMap = jsonToHashMap;
    }

    public void sendAdhocEmail(String title, String body,
                               MultipartFile attachment, File logFile){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(this.jsonToHashMap.toHashMap(generalEmailJsonPath).get("fromEmail"));
            messageHelper.setTo(this.jsonToHashMap.toHashMap(adhocEmailJsonPath).get("recipient").split(","));
            messageHelper.setSubject(title);
            messageHelper.setText(builder.buildEmail(body), true);
            messageHelper.addAttachment(attachment.getOriginalFilename(), attachment);
            messageHelper.addAttachment(logFile.getName(), logFile);
        };
        try {
            mailSender.send(messagePreparator);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            LOG.error("An error occurred during email send." + e);
        }
    }

    public void sendAutoEmail(File logFile){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(this.jsonToHashMap.toHashMap(generalEmailJsonPath).get("fromEmail"));
            messageHelper.setTo(this.jsonToHashMap.toHashMap(autoEmailJsonPath).get("recipient").split(","));
            messageHelper.setSubject(this.jsonToHashMap.toHashMap(autoEmailJsonPath).get("subject"));
            messageHelper.setText(builder.buildEmail(
                    this.jsonToHashMap.toHashMap(autoEmailJsonPath).get("message")), true);
            messageHelper.addAttachment(logFile.getName(), logFile);
        };
        try {
            mailSender.send(messagePreparator);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            LOG.error("An error occurred during email send." + e);
        }
    }
}

package com.powerapps.monitor.service;

import com.powerapps.monitor.config.GetEmailSettings;
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
    private final GetEmailSettings getEmailSettings;

    private static final Logger LOG = LoggerFactory.getLogger(EmailClient.class);

    @Autowired
    public EmailClient(JavaMailSender mailSender,
                       MailContentBuilder builder,
                       GetEmailSettings getEmailSettings){
        this.mailSender = mailSender;
        this.builder = builder;
        this.getEmailSettings=getEmailSettings;
    }

    public void sendAdhocEmail(String title, String body,
                               MultipartFile attachment, File logFile){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            String recipient = this.getEmailSettings.getSettings(adhocEmailJsonPath).get("recipient");
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(this.getEmailSettings.getSettings(generalEmailJsonPath).get("fromEmail"));
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            String content = builder.buildEmail(body);
            messageHelper.setText(content, true);
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

    public void sendAutoEmail(){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            String recipient = this.getEmailSettings.getSettings(adhocEmailJsonPath).get("recipient");
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(this.getEmailSettings.getSettings(generalEmailJsonPath).get("fromEmail"));
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(this.getEmailSettings.getSettings(adhocEmailJsonPath).get("subject"));
            String content = builder.buildEmail(
                    this.getEmailSettings.getSettings(adhocEmailJsonPath).get("message"));
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            LOG.error("An error occurred during email send." + e);
        }
    }
}

package com.powerapps.monitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailClient {
    private JavaMailSender mailSender;
    private String emailFrom = "abc@gmail.com";
    private MailContentBuilder builder;
    private static final Logger LOG = LoggerFactory.getLogger(EmailClient.class);

    @Autowired
    public EmailClient(JavaMailSender mailSender,
                       MailContentBuilder builder){
        this.mailSender = mailSender;
        this.builder = builder;
    }

    public void sendEmail(String recipient,String subject, String intro,
                          String message){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(subject);
            String content = builder.buildEmail(intro, message);
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

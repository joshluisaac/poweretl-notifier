package com.powerapps.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailConfig {
    private final GetEmailSettings getEmailSettings;

    @Value("${app.generalEmailJson}")
    private String generalEmailJsonPath;

    public EmailConfig(GetEmailSettings getEmailSettings){
        this.getEmailSettings=getEmailSettings;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(
                this.getEmailSettings.getSettings(generalEmailJsonPath).get("host"));
        javaMailSender.setPort(
                Integer.valueOf(
                        this.getEmailSettings.getSettings(
                                generalEmailJsonPath).get("port")));
        javaMailSender.setUsername(this.getEmailSettings.getSettings(
                generalEmailJsonPath).get("username"));
        javaMailSender.setPassword(this.getEmailSettings.getSettings(
                generalEmailJsonPath).get("password"));
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.debug", "false");
        return properties;
    }
}

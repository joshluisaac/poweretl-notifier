package com.powerapps.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * This class is an implementation of the JavaMailSender
 * Used when sending email. It sets the necessary information needed such as
 * username
 * password
 * host
 * port
 * protocol
 * DefaultEncoding
 * It also sets a few extra email properties such as tls, debug and authentication.
 * @author hashim
 */

@Component
public class EmailConfig {
    private final JsonToHashMap jsonToHashMap;

    @Value("${app.generalEmailJson}")
    private String generalEmailJsonPath;

    public EmailConfig(JsonToHashMap jsonToHashMap){
        this.jsonToHashMap = jsonToHashMap;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(
                this.jsonToHashMap.toHashMap(generalEmailJsonPath).get("host"));
        javaMailSender.setPort(
                Integer.valueOf(
                        this.jsonToHashMap.toHashMap(
                                generalEmailJsonPath).get("port")));
        javaMailSender.setUsername(this.jsonToHashMap.toHashMap(
                generalEmailJsonPath).get("username"));
        javaMailSender.setPassword(this.jsonToHashMap.toHashMap(
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

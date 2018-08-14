package com.powerapps.monitor.config;

import com.kollect.etl.notification.config.IEmailConfig;
import com.powerapps.monitor.util.JsonToHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of the JavaMailSender and depends on the etl-notification Config class
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
        final IEmailConfig config = new com.kollect.etl.notification.config.EmailConfig();
        return config.setEmailSettings(this.jsonToHashMap.toHashMap(generalEmailJsonPath).get("host"),
                Integer.valueOf(
                        this.jsonToHashMap.toHashMap(
                                generalEmailJsonPath).get("port")),
                this.jsonToHashMap.toHashMap(
                        generalEmailJsonPath).get("username"),
                this.jsonToHashMap.toHashMap(
                        generalEmailJsonPath).get("password"),
                "true", "false", "false"
        );
    }
}

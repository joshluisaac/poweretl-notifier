package com.powerapps.monitor.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.kollect.etl.notification.config.IEmailConfig;
import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.util.JsonUtils;


@Component
@ComponentScan({"org.springframework.mail.javamail","com.kollect.etl.notification.config","com.kollect.etl.notification.service","com.kollect.etl.notification.entity"})
public class EmailConfigAssembler {
  
  @Value("${app.generalEmailJson}")
  private String generalEmailJsonPath;
  private JsonUtils jsonUtils = new JsonUtils();
  private IEmailConfig config;
  private final Logger logger = LoggerFactory.getLogger(EmailConfigAssembler.class);
  
  @Autowired
  EmailConfigAssembler(IEmailConfig config){
    this.config = config;
  }
  
  @Bean
  public JavaMailSender javaMailService(EmailConfigEntity configEntity) {
    JavaMailSender mailer = config.setEmailSettings(configEntity);
    logger.info("Initialized and constructed JavaMailSender Bean");
    return mailer;
  }
  
  
  @Bean
  public EmailConfigEntity deSerializeConfig() {
    EmailConfigEntity configEntity = null;
    try (Reader reader = new FileReader(new File(generalEmailJsonPath))) {
      configEntity = jsonUtils.fromJson(reader, EmailConfigEntity.class);
      logger.info("Initialized and constructed EmailConfigEntity Bean");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return configEntity;
  }
  

}

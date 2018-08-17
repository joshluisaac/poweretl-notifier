package com.powerapps.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.kollect.etl.notification.config.EmailConfig;

//@Component
public class BeanInitz {
  
  //@Bean
  public EmailConfig initializeEmailConfigBean() {
    return new EmailConfig();
  }

}

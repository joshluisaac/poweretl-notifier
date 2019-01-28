package com.powerapps.monitor.dataconnector;

import com.kollect.etl.util.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Component
public class DcEmailConfigurationBeanInitializer {


  private final Logger LOG = LoggerFactory.getLogger(DcEmailConfigurationBeanInitializer.class);

  @Value("${app.dc.email.config}")
  private String dcEmailConfig;


  @Bean
  public Map<String,DcEmailConfiguration> load() throws FileNotFoundException {
    LOG.info("Initialized and constructed DC email configuration bean");
    Map<String,DcEmailConfiguration> m = new DcEmailConfigurationReader().jsonDeSerializer(new FileUtils().getFileFromClasspath(dcEmailConfig));
    return m;
  }

}

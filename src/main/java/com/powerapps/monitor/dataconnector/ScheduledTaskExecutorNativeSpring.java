package com.powerapps.monitor.dataconnector;

import com.powerapps.monitor.service.DataConnectorNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ScheduledTaskExecutorNativeSpring {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduledTaskExecutorNativeSpring.class);
  private final DataConnectorNotification dcNotificationService;
  private Map<String,DcEmailConfiguration> configMap;


  @Autowired
  public ScheduledTaskExecutorNativeSpring(DataConnectorNotification dcNotificationService, Map<String,DcEmailConfiguration> configMap) {
    this.dcNotificationService = dcNotificationService;
    this.configMap = configMap;
  }

  //@Scheduled(fixedRateString = "10000")
  public void executeTask()throws Exception {
    DcEmailConfiguration config = configMap.get("BR");
    LOG.info("Executing DC email");
    dcNotificationService.execute(
            config.getTitle(),
            config.getServerLogPath(),
            config.getContext(),
            config.getRecipients(),
            config.getServerLogDir(),
            config.daysAgo,
            config.renotify);
  }


}

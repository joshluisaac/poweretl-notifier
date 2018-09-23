package com.powerapps.monitor.tasks;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.powerapps.monitor.service.BatchManagerLogNotificationService;
import com.powerapps.monitor.service.DataConnectorNotification;

@Component
public class MailTaskScheduler extends Scheduler {
  
  private final Logger logger = LoggerFactory.getLogger(MailTaskScheduler.class);
  
  
  public MailTaskScheduler(BatchManagerLogNotificationService bmService,
      DataConnectorNotification dcNotificationService) {
    super(bmService, dcNotificationService);
  }



  @Value("#{ ${app.yyc.dc.scheduler.enable} eq true ? true : false }")
  boolean yycDcEnableSchedler;
  @Value("${app.yyc.dc.serverLogPath}")
  String yycDcServerLogPath;
  @Value("${app.yyc.dc.emailTitle}")
  String yycDcEmailTitle;
  @Value("${app.yyc.dc.emailContext}")
  String yycDcEmailContext;
  @Value("${app.yyc.dc.recepients}")
  String yycDcEmailRecepients;
  @Value("${app.yyc.dc.scheduler.cronexpression}")
  String cronExpression;
  
  
  
  @Scheduled(cron = "${app.yyc.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailYYC() throws IOException {
    logger.info("Firing email task at {}",cronExpression);
    if(yycDcEnableSchedler) dcNotificationService.execute(yycDcEmailTitle, yycDcServerLogPath, yycDcEmailContext, yycDcEmailRecepients);
  }
  
  

}

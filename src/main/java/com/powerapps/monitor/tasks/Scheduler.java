package com.powerapps.monitor.tasks;

import com.powerapps.monitor.service.BatchManagerLogNotificationService;
import com.powerapps.monitor.service.DataConnectorNotification;
import com.powerapps.monitor.service.TransferedFileServce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class runs the core scheduling tasks. It takes in the time values from
 * the properties file and runs a scheduled task.
 *
 * @author hashim
 */
@Component
@PropertySource("classpath:config/fixedSchedulerConfig.properties")
public class Scheduler {
  /* Required services */
  protected final BatchManagerLogNotificationService bmService;
  protected final DataConnectorNotification dcNotificationService;
  protected final TransferedFileServce transferService;

  /* Required variables */

  
  
  @Value("#{ ${app.bm.scheduler.enable} eq true ? true : false }")
  boolean bmEnableSchedler;
  @Value("#{ ${app.dc.scheduler.enable} eq true ? true : false }")
  boolean dcEnableSchedler;
  @Value("${app.dc.serverLogPath}")
  String dcServerLogPath;
  @Value("${app.dc.emailTitle}")
  String dcEmailTitle;
  @Value("${app.dc.emailContext}")
  String dcEmailContext;
  @Value("${app.mbsb.bm.recepients}")
  String bmEmailRecepients;

  
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



  @Value("#{ ${app.cco.dc.scheduler.enable} eq true ? true : false }")
  boolean ccoDcEnableSchedler;
  @Value("${app.cco.dc.serverLogPath}")
  String ccoDcServerLogPath;
  @Value("${app.cco.dc.emailTitle}")
  String ccoDcEmailTitle;
  @Value("${app.cco.dc.emailContext}")
  String ccoDcEmailContext;
  @Value("${app.cco.dc.recepients}")
  String ccoDcEmailRecepients;



  @Value("#{ ${app.ecollecthp.dc.scheduler.enable} eq true ? true : false }")
  boolean ecollecthpDcEnableSchedler;
  @Value("${app.ecollecthp.dc.serverLogPath}")
  String ecollecthpDcServerLogPath;
  @Value("${app.ecollecthp.dc.emailTitle}")
  String ecollecthpDcEmailTitle;
  @Value("${app.ecollecthp.dc.emailContext}")
  String ecollecthpDcEmailContext;
  @Value("${app.ecollecthp.dc.recepients}")
  String ecollecthpDcEmailRecepients;

  private final Logger logger = LoggerFactory.getLogger(Scheduler.class);

  /* The constructor for the class to inject the necessary services */
  @Autowired
  public Scheduler(BatchManagerLogNotificationService bmService,

      DataConnectorNotification dcNotificationService, TransferedFileServce transferService) {
    this.bmService = bmService;
    this.dcNotificationService = dcNotificationService;
    this.transferService = transferService;
  }

  @Scheduled(fixedRateString = "${interval}")
  public void runFixedSchedule() throws IOException {
    if(bmEnableSchedler) bmService.execute(bmEmailRecepients);
  }
  
}
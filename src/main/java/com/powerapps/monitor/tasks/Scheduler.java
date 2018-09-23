package com.powerapps.monitor.tasks;

import com.powerapps.monitor.service.BatchManagerLogNotificationService;
import com.powerapps.monitor.service.DataConnectorNotification;
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
  @Value("${app.dc.recepients}")
  String dcEmailRecepients;

  
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

  @Value("#{ ${app.pbk.dc.scheduler.enable} eq true ? true : false }")
  boolean pbkDcEnableSchedler;
  @Value("${app.pbk.dc.serverLogPath}")
  String pbkDcServerLogPath;
  @Value("${app.pbk.dc.emailTitle}")
  String pbkDcEmailTitle;
  @Value("${app.pbk.dc.emailContext}")
  String pbkDcEmailContext;
  @Value("${app.pbk.dc.recepients}")
  String pbkDcEmailRecepients;

  @Value("#{ ${app.pelita.dc.scheduler.enable} eq true ? true : false }")
  boolean pelitaDcEnableSchedler;
  @Value("${app.pelita.dc.serverLogPath}")
  String pelitaDcServerLogPath;
  @Value("${app.pelita.dc.emailTitle}")
  String pelitaDcEmailTitle;
  @Value("${app.pelita.dc.emailContext}")
  String pelitaDcEmailContext;
  @Value("${app.pelita.dc.recepients}")
  String pelitaDcEmailRecepients;

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

  @Value("#{ ${app.mbsb.dc.scheduler.enable} eq true ? true : false }")
  boolean mbsbDcEnableSchedler;
  @Value("${app.mbsb.dc.serverLogPath}")
  String mbsbDcServerLogPath;
  @Value("${app.mbsb.dc.emailTitle}")
  String mbsbDcEmailTitle;
  @Value("${app.mbsb.dc.emailContext}")
  String mbsbDcEmailContext;
  @Value("${app.mbsb.dc.recepients}")
  String mbsbDcEmailRecepients;

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

      DataConnectorNotification dcNotificationService) {
    this.bmService = bmService;
    this.dcNotificationService = dcNotificationService;
  }

  @Scheduled(fixedRateString = "${interval}")
  public void runFixedSchedule() throws IOException {
    if(bmEnableSchedler) bmService.execute(dcEmailRecepients);
  }

  @Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmail() throws IOException {
    if(dcEnableSchedler) dcNotificationService.execute(dcEmailTitle, dcServerLogPath, dcEmailContext, dcEmailRecepients);
  }

  //@Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmailYYC() throws IOException {
    if(yycDcEnableSchedler) dcNotificationService.execute(yycDcEmailTitle, yycDcServerLogPath, yycDcEmailContext, yycDcEmailRecepients);
  }

  @Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmailPBK() throws IOException {
    if(pbkDcEnableSchedler) dcNotificationService.execute(pbkDcEmailTitle, pbkDcServerLogPath, pbkDcEmailContext, pbkDcEmailRecepients);
  }

  @Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmailPelita() throws IOException {
    if(pelitaDcEnableSchedler) dcNotificationService.execute(pelitaDcEmailTitle, pelitaDcServerLogPath, pelitaDcEmailContext,
        pelitaDcEmailRecepients);
  }

  @Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmailCCO() throws IOException {
    if(ccoDcEnableSchedler) dcNotificationService.execute(ccoDcEmailTitle, ccoDcServerLogPath, ccoDcEmailContext, ccoDcEmailRecepients);
  }

  @Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmailMBSB() throws IOException {
    if(mbsbDcEnableSchedler) dcNotificationService.execute(mbsbDcEmailTitle, mbsbDcServerLogPath, mbsbDcEmailContext, mbsbDcEmailRecepients);
  }

  @Scheduled(fixedRateString = "${interval}")
  public void sendDataConnectorStatsEmailEcollectHp() throws IOException {
    if(ecollecthpDcEnableSchedler) dcNotificationService.execute(ecollecthpDcEmailTitle, ecollecthpDcServerLogPath, ecollecthpDcEmailContext,
        ecollecthpDcEmailRecepients);
  }

  // @Scheduled(fixedRateString = "${interval}")
  public void run1() throws IOException, InterruptedException {
    logger.info("Running...{} using {}", "run1", Thread.currentThread().getName());
    Thread.sleep(10000);
    logger.info("Still running...{} using {}", "run1", Thread.currentThread().getName());
  }

  // @Scheduled(fixedRateString = "${interval}")
  public void run2() throws IOException {
    logger.info("Running...{} using {}", "run2", Thread.currentThread().getName());
  }

}
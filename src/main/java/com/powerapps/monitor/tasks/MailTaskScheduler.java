package com.powerapps.monitor.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.powerapps.monitor.service.BatchManagerLogNotificationService;
import com.powerapps.monitor.service.DataConnectorNotification;
import com.powerapps.monitor.service.TransferedFileServce;

@Component
public class MailTaskScheduler extends Scheduler {

  private final Logger logger = LoggerFactory.getLogger(MailTaskScheduler.class);

  public MailTaskScheduler(BatchManagerLogNotificationService bmService,
      DataConnectorNotification dcNotificationService, TransferedFileServce transferService) {
    super(bmService, dcNotificationService,transferService);
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
  @Value("${app.yyc.dc.serverLogDir}")
  String serverLogDir;

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
  @Value("${app.pbk.dc.scheduler.cronexpression}")
  String cronExpressionPBK;
  @Value("${app.pbk.dc.serverLogDir}")
  String serverLogDirPBK;

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
  @Value("${app.pelita.dc.scheduler.cronexpression}")
  String cronExpressionPelita;
  @Value("${app.pelita.dc.serverLogDir}")
  String serverLogDirPelita;
  
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
  @Value("${app.cco.dc.scheduler.cronexpression}")
  String cronExpressioncco;
  @Value("${app.cco.dc.serverLogDir}")
  String serverLogDircco;
  
  
  @Value("#{ ${app.ictzone.dc.scheduler.enable} eq true ? true : false }")
  boolean ictzoneDcEnableSchedler;
  @Value("${app.ictzone.dc.serverLogPath}")
  String ictzoneDcServerLogPath;
  @Value("${app.ictzone.dc.emailTitle}")
  String ictzoneDcEmailTitle;
  @Value("${app.ictzone.dc.emailContext}")
  String ictzoneDcEmailContext;
  @Value("${app.ictzone.dc.recepients}")
  String ictzoneDcEmailRecepients;
  @Value("${app.ictzone.dc.scheduler.cronexpression}")
  String cronExpressionictzone;
  @Value("${app.ictzone.dc.serverLogDir}")
  String serverLogDirictzone;
  
  
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
  @Value("${app.mbsb.dc.scheduler.cronexpression}")
  String cronExpressionMbsb;
  @Value("${app.mbsb.dc.serverLogDir}")
  String serverLogDirMbsb;
  
  @Value("#{ ${app.mbsb.dc.transfer.scheduler.enable} eq true ? true : false }")
  boolean mbsbTransferEnableSchedler;
  @Value("${app.mbsb.dc.transfer.scheduler.cronexpression}")
  String transferCronExpression;
  
  
  
  @Scheduled(cron = "${app.yyc.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailYYC() throws Exception {
    logger.info("Firing email task at {}", cronExpression);
    if (yycDcEnableSchedler)
      dcNotificationService.execute(yycDcEmailTitle, yycDcServerLogPath, yycDcEmailContext, yycDcEmailRecepients,
          serverLogDir);
  }

  @Scheduled(cron = "${app.pbk.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailPBK() throws Exception {
    if (pbkDcEnableSchedler)
      dcNotificationService.execute(pbkDcEmailTitle, pbkDcServerLogPath, pbkDcEmailContext, pbkDcEmailRecepients,
          serverLogDirPBK);
  }

  @Scheduled(cron = "${app.pelita.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailPelita() throws Exception {
    if (pelitaDcEnableSchedler)
      dcNotificationService.execute(pelitaDcEmailTitle, pelitaDcServerLogPath, pelitaDcEmailContext,
          pelitaDcEmailRecepients, serverLogDirPelita);
  }
  
  
  @Scheduled(cron = "${app.cco.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailCco() throws Exception {
    if (ccoDcEnableSchedler)
      dcNotificationService.execute(ccoDcEmailTitle, ccoDcServerLogPath, ccoDcEmailContext,
          ccoDcEmailRecepients, serverLogDircco);
  }
  
  @Scheduled(cron = "${app.ictzone.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailictzone() throws Exception {
    if (ictzoneDcEnableSchedler)
      dcNotificationService.execute(ictzoneDcEmailTitle, ictzoneDcServerLogPath, ictzoneDcEmailContext,
          ictzoneDcEmailRecepients, serverLogDirictzone);
  }
  
  
  @Scheduled(cron = "${app.mbsb.dc.scheduler.cronexpression}")
  public void sendDataConnectorStatsEmailMbsb() throws Exception {
    logger.info("Firing email task at {}", cronExpressionMbsb);
    if (mbsbDcEnableSchedler)
      dcNotificationService.execute(mbsbDcEmailTitle, mbsbDcServerLogPath, mbsbDcEmailContext, 
          mbsbDcEmailRecepients,
          serverLogDirMbsb);
  }
  
  @Scheduled(cron = "${app.mbsb.dc.transfer.scheduler.cronexpression}")
  public void sendDataTransferStatsEmailMbsb() throws Exception {
    logger.info("Firing data transfer email task at {}", transferCronExpression);
    if (mbsbTransferEnableSchedler)
      transferService.execute(mbsbDcEmailRecepients,mbsbDcEmailContext);
  }

}

package com.powerapps.monitor.dataconnector;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class DcEmailConfiguration {

  String recipients;
  String operator;
  String isEnabled;
  String cronExpression;
  String serverLogPath;
  String serverLogDir;
  String title;
  String context;
  String additionalMsg;
  String tenant;
  String renotify;
  String daysAgo;

  public DcEmailConfiguration(String recipients, String operator, String isEnabled,
                              String cronExpression, String serverLogPath, String serverLogDir, String title,
                              String context, String additionalMsg, String tenant, String daysAgo, String renotify) {
    this.recipients = recipients;
    this.operator = operator;
    this.isEnabled = isEnabled;
    this.cronExpression = cronExpression;
    this.serverLogPath = serverLogPath;
    this.serverLogDir = serverLogDir;
    this.title = title;
    this.context = context;
    this.additionalMsg = additionalMsg;
    this.tenant = tenant;
    this.daysAgo = daysAgo;
    this.renotify = renotify;
  }

  public String getRecipients() {
    return recipients;
  }

  public void setRecipients(String recipients) {
    this.recipients = recipients;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public String getServerLogPath() {
    return serverLogPath;
  }

  public void setServerLogPath(String serverLogPath) {
    this.serverLogPath = serverLogPath;
  }

  public String getServerLogDir() {
    return serverLogDir;
  }

  public void setServerLogDir(String serverLogDir) {
    this.serverLogDir = serverLogDir;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getAdditionalMsg() {
    return additionalMsg;
  }

  public void setAdditionalMsg(String additionalMsg) {
    this.additionalMsg = additionalMsg;
  }


  public String getTenant() {
    return tenant;
  }

  public void setTenant(String tenant) {
    this.tenant = tenant;
  }

  public String getRenotify() {
    return renotify;
  }

  public void setRenotify(String renotify) {
    this.renotify = renotify;
  }

  public String getDaysAgo() {
    return daysAgo;
  }

  public void setDaysAgo(String daysAgo) {
    this.daysAgo = daysAgo;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("recipients", recipients)
            .add("operator", operator)
            .add("isEnabled", isEnabled)
            .add("cronExpression", cronExpression)
            .add("serverLogPath", serverLogPath)
            .add("serverLogDir", serverLogDir)
            .add("title", title)
            .add("context", context)
            .add("additionalMsg", additionalMsg)
            .add("tenant", tenant)
            .add("renotify", renotify)
            .add("daysAgo", daysAgo)
            .toString();
  }
}

package com.powerapps.monitor.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Configuration //added this bean for spring to find and make it a candidate for injection
@ConfigurationProperties("app")
@Validated
public class AppConfigProperties {
  
  private String emailMaxQueueSize, daysAgo, extractionEmailLogPath;
  private DcNotification notif = new DcNotification();
  private Security security = new Security();
  private Attachment attachment = new Attachment();
  
  
  public String getEmailMaxQueueSize() {
    return emailMaxQueueSize;
  }


  public void setEmailMaxQueueSize(String emailMaxQueueSize) {
    this.emailMaxQueueSize = emailMaxQueueSize;
  }


  public String getDaysAgo() {
    return daysAgo;
  }


  public void setDaysAgo(String daysAgo) {
    this.daysAgo = daysAgo;
  }


  public String getExtractionEmailLogPath() {
    return extractionEmailLogPath;
  }


  public void setExtractionEmailLogPath(String extractionEmailLogPath) {
    this.extractionEmailLogPath = extractionEmailLogPath;
  }


  public DcNotification getNotif() {
    return notif;
  }


  public void setNotif(DcNotification notif) {
    this.notif = notif;
  }
  
  
  
  public Security getSecurity() {
    return security;
  }


  public void setSecurity(Security security) {
    this.security = security;
  }



  public Attachment getAttachment() {
    return attachment;
  }


  public void setAttachment(Attachment attachment) {
    this.attachment = attachment;
  }



  public static class Security{
    String username, password;
    private boolean enabled;
    
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    public boolean isEnabled() {
      return enabled;
    }
    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }
    @Override
    public String toString() {
      return "Security [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
    }
    
    
  }

  public static class Attachment{
    int maxlimit;

    
    
    public int getMaxlimit() {
      return maxlimit;
    }



    public void setMaxlimit(int maxlimit) {
      this.maxlimit = maxlimit;
    }



    @Override
    public String toString() {
      return "Attachment [maxlimit=" + maxlimit + "]";
    }
    
    
  }
  

  public static class DcNotification {
    private String renotify;
    private String mode;
    
    public String getRenotify() {
      return renotify;
    }
    public void setRenotify(String renotify) {
      this.renotify = renotify;
    }
    
    public String getMode() {
      return mode;
    }
    public void setMode(String mode) {
      this.mode = mode;
    }
    
    @Override
    public String toString() {
      return "Dcnotification [mode=" + mode + ", renotify=" + renotify + "]";
    }
    
    
  }


  @Override
  public String toString() {
    return "AppConfigProperties [emailMaxQueueSize=" + emailMaxQueueSize + ", daysAgo=" + daysAgo
        + ", extractionEmailLogPath=" + extractionEmailLogPath + ", notif=" + notif + ", security=" + security
        + ", attachment=" + attachment + "]";
  }

  
  
}

package com.powerapps.monitor.model;

public class ResponseTransfer {
  private String username;
  private String password;

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }


  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public String toString() {
    return "ResponseTransfer{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}

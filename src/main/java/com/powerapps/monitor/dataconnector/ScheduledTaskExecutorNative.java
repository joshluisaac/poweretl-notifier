package com.powerapps.monitor.dataconnector;

import com.kollect.etl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTaskExecutorNative {

    private static final long PERIOD = 3600000;
    private DcEmailTask emailTask;


    @Autowired
    public ScheduledTaskExecutorNative(DcEmailTask emailTask) {
        this.emailTask = emailTask;
    }


  @Bean
  public ScheduledFuture execute(){
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY,9);
      calendar.set(Calendar.MINUTE,58);
      calendar.set(Calendar.SECOND,00);
      calendar.set(Calendar.MILLISECOND,0);
      Date d = new Date(calendar.getTime().getTime() - System.currentTimeMillis());
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture handle = executor.scheduleAtFixedRate(emailTask.run,d.getTime(),PERIOD, TimeUnit.MILLISECONDS);
    return handle;
  }





}

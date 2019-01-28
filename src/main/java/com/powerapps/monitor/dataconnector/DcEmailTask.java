package com.powerapps.monitor.dataconnector;

import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.service.DataConnectorNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class DcEmailTask {

    private static final Logger LOG = LoggerFactory.getLogger(DcEmailTask.class);
    private int x = 0;

    protected final DataConnectorNotification dcNotificationService;
    Map<String,DcEmailConfiguration> configMap;


    @Autowired
    public DcEmailTask(DataConnectorNotification dcNotificationService, Map<String,DcEmailConfiguration> configMap) {
        this.dcNotificationService = dcNotificationService;
        this.configMap = configMap;
    }


    public Runnable run = () -> {
        try {
            executeTask();
        } catch (Exception e) {
            LOG.error("Caught exception in ScheduledExecutorService. StackTrace:\n" + e.getStackTrace());
        }
    };


    public void executeTask()throws Exception {
        DcEmailConfiguration config = configMap.get("BR");
        dcNotificationService.execute(
                config.getTitle(),
                config.getServerLogPath(),
                config.getContext(),
                config.getRecipients(),
                config.getServerLogDir(),
                config.daysAgo,
                config.renotify);
    }


    void timerTask() throws Exception {
        Timer t = new Timer();
        System.out.println(Thread.currentThread().getName());
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Name of this thread: " + Thread.currentThread().getName());
            }
        }, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-01-25 17:17:01"), 10000);
    }
}

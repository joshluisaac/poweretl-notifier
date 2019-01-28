package com.powerapps.monitor.dataconnector;

import com.powerapps.monitor.service.DataConnectorNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AdhocEmailController {

    private static final long PERIOD = 3600000;
    private DataConnectorNotification dcNotificationService;


    @Autowired
    public AdhocEmailController(DataConnectorNotification dcNotificationService) {
        this.dcNotificationService = dcNotificationService;
    }


    @PostMapping("sendDcAdhocEmail")
    @ResponseBody
    public String sendDcAdhocEmail(@RequestBody DcEmailConfiguration body) throws Exception{

        System.out.println("Request Body: " + body.toString());

        //dcNotificationService.execute(null,null,null,null,null,null,null);
        return "someString";
    }








}

package com.powerapps.monitor.dataconnector;

import com.powerapps.monitor.service.DataConnectorNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UrgentDcEmailController {

    private DataConnectorNotification dcNotificationService;


    @Autowired
    public UrgentDcEmailController(DataConnectorNotification dcNotificationService) {
        this.dcNotificationService = dcNotificationService;
    }


    @PostMapping("api/v1.0/dc/sendemail")
    @ResponseBody
    public String sendDcAdhocEmail(@RequestBody DcEmailConfiguration body) throws Exception {
        if( !body.getIsEnabled().equals("true") ) {
            return "Email Unsent. Please check \"isEnabled\" property and ensure it is set to true";
        }
        String status = dcNotificationService.execute(
                body.getTitle(),
                body.getServerLogPath(),
                body.getContext(),
                body.getRecipients(),
                body.getServerLogDir(),
                body.getDaysAgo(),
                body.getRenotify());
        return status;
    }








}

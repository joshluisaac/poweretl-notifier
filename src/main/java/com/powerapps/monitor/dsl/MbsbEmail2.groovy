package com.powerapps.monitor.dsl

import com.kollect.etl.notification.entity.EmailConfigEntity
import com.kollect.etl.notification.service.IEmailClient
import com.kollect.etl.notification.service.IEmailContentBuilder
import com.powerapps.monitor.model.BmProperties
import com.powerapps.monitor.service.BatchManagerLogService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MbsbEmail2 {

    private static final Logger LOG = LoggerFactory.getLogger(MbsbEmail2.class)
    public final BatchManagerLogService logService
    private final IEmailContentBuilder emailContentBuilder
    private final IEmailClient emailClient
    private final EmailConfigEntity emailConfig

    public MbsbEmail2(BatchManagerLogService logService){
        this.logService  = logService
    }


    static void main(String[] args) {

        new BmProperties()

new MbsbEmail2(new BatchManagerLogService())
    }


}

#!/usr/bin/env bash


curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
        "recipients": "nwankwo.joshua@gmail.com,joshua@kollect.my",
        "operator": "nwankwo.joshua@gmail.com,joshua@kollect.my",
        "isEnabled": "false",
        "cronExpression": "* 45 *",
        "serverLogPath": "/home/joshua/Downloads/br_dc_sample_logs/log.log",
        "serverLogDir": "/home/joshua/Downloads/br_dc_sample_logs/",
        "title": "SUCCESSFUL: BR Completion of PowerKollect Daily Data Loading",
        "context": "br",
        "additionalMsg": "",
        "tenant": "br",
        "daysAgo": "5",
        "renotify": "true"
}' \
http://localhost:8088/api/v1.0/dc/sendemail
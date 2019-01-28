# Dev ops guide


## BatchManager Email Notification 
This email notifies users on the status of batch jobs. 
This includes the batch name, batch running time, start/end time and batch status.

A sample email is presented in the figure below

![Alt text][bmemail]

Figure 1: BatchManager email notification.


## BatchManager Email Notification  -  Settings

A setup is required to get notification running for Batch Manager (BM).

The following will need to be updated to suit your environment.


`bmRootPath` This is the path to where BatchManager logs resides.

`batchStartRegex` Regex used to identify the start position of the contents of the log file (Default settings, please don't modify)

`batchDoneRegex` Regex used to identify the end position of the contents of the log file  (Default settings, please don't modify)

`batchErrorRegex` Regex used to identify the errors in the contents of the log file  (Default settings, please don't modify)

`bmCache` Stores the history of BM sent emails. (Default to `../out/bmcache.csv`)

`app.bm.scheduler.enable` Enables BM scheduler cron

`app.bm.emailTitle` BM email title.


## How do i update these settings?

There are two ways to update these settings. The first method is directly editing the configuration JSON file (not recommended) and the second method is updating using the provided user interface.

### Method 1: Directly updating [bmconfig.json](src/main/resources/config/bmconfig.json) (not recommended)
`bmconfig.json` is the configuration file which stores these settings. 

```json
{
  "bmRootPath": "/media/joshua/martian2/kollectprojects/mbsb/_mbsb_prd_logs/logs/",
  "batchStartRegex": "^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (DEBUG) (--START--)",
  "batchDoneRegex": "^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (DEBUG) (--DONE--)",
  "batchErrorRegex": "^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (ERROR)",
  "bmCache": "/media/joshua/martian/linked_projects/notifier/out/bmcache.csv"
}

```

Update the following properties in [application.properties](src/main/resources/application.properties)

```properties

app.bm.scheduler.enable=false
app.bm.emailTitle=MBSB Completion of PowerKollect Daily Batch loading for 

```

### Method 2: Updating settings from UI (recommended)

A cleaner and less error prone method of updating BM settings is by using the UI. 


![Alt text][bmConfigImage]


## DataConnector Email Notification 

This email notifies users of the following metrics:
* Number of records that was extracted from the core.
* Number of records that was loaded from the data file which came from the core.
* Number of records that was rejected after loading.
* Running time which is the duration loading took.
* Throught put/sec: Number of records that was processed per second.

![Alt text][dcemail]

Figure 2: DataConnector email notification.


## DataConnector Email Notification  - Settings

A setup is required to get notification running for DataConnector (DC).


```properties
#flag to enable DC scheduler
app.ictzone.dc.scheduler.enable=false

#DC scheduler cron expression
app.ictzone.dc.scheduler.cronexpression=0 18 19 * * *

#Comma separated list of recipients
app.ictzone.dc.recepients=kvloading@kollect.my

#Path to folder where Server.log resides 
app.ictzone.dc.serverLogDir=/home/kvalleydb/etl/ictzone-loading/logs/

#Path to Server.log file
app.ictzone.dc.serverLogPath=/home/kvalleydb/etl/ictzone-loading/logs/Server.log

#DC email title
app.ictzone.dc.emailTitle=ICTZone KollectValley System Alert: Data Loading

#DC unique identifier.Update it to the name of the project
app.ictzone.dc.emailContext=ictzone

app.ictzone.dc.additionalMessages=


```


## Sending DC emails from terminal
Using an API endpoint you could send DC email notification using the command below. This script comes handy in SIT and UAT environments when you need to send an email notification on urgent or ad-hoc basis. 

```bash
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


```


## SFTP Email Notification 

This email alerts users of the number of files, file size, number of records and 
status of data files which has been transferred from the core to PowerApps staging environment.

A sample email notification for data files transport is presented below

![Alt text][transportEmail]

Figure 3: Data transfer email notification.



## SFTP Email Notification - Settings


```properties

app.mbsb.dc.transfer.scheduler.enable=false
app.mbsb.dc.transfer.recepients=joshua@kollect.my
app.mbsb.dc.transfer.scheduler.cronexpression=0 21 13 * * *
app.mbsb.dc.transfer.emailTitle=SUCCESSFUL: MBSB Completion of PowerKollect Daily Data Transfer
app.mbsb.dc.transfer.schemaFile=config/transferSchema.txt
app.mbsb.dc.transfer.stageDir=/ftp/ffiles/Daily
```






[bmConfigImage]: bm_ui_settings.png "Logo Title Text 2"
[transportEmail]: transport_email.png "Logo Title Text 2"
[bmemail]: bmemail.png "Logo Title Text 2"
[dcemail]: dc_email.png "Logo Title Text 2"










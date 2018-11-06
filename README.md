# PowerETL Notifier
PowerApps notification and monitoring app



## Sections
1. [Building PowerETL Notifier](#build)

1. [Starting PowerETL Notifier](#start)
2. [Stopping PowerETL Notifier](#stop)


---
## Build
Building the project will download the required dependencies, assemble the artefact into a build structure and generate a zip file for deployment. Execute __rebuild.sh__ to accomplish this. The build zip file can be found at the project root directory with a timestamp naming convention.  

The naming convention uses the format `poweretl-notifier_%Y%m%d_%H%M%S.zip`

---

--
## Start
Navigate into the bin directory and run __startPowerEtlNotifier.sh__, this would start PowerETL Notifier and listening at the designated port number. The port number is a configurable property in __resources/application.properties__

--

--
## Stop
In the bin directory is a bash file called __stopPowerEtlNotifier.sh__, this would retrieve the process id from __logs/procid__ file and then kill the process. Execute __stopPowerEtlNotifier.sh__ to stop PowerETL Notifier. 

--




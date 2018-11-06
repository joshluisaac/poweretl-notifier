# PowerETL Notifier
Notifies metrics, statistics and monitoring data exposing that through REST APIs for consumption by other tools and sub-systems.



## Sections
1. [Building PowerETL Notifier](#build)
2. [Starting PowerETL Notifier](#start)
3. [Stopping PowerETL Notifier](#stop)


---

## Build
Building the project will download the required dependencies, assemble the artefact into a build structure and generate a zip file for deployment. Execute *build.sh* to accomplish this. The build zip file can be found at the project root directory with a timestamp naming convention.  

The naming convention uses the format `poweretl-notifier_%Y%m%d_%H%M%S.zip`



---
## Start
Navigate into the bin directory and run *startPowerEtlNotifier.sh*, this would start PowerETL Notifier and listening at the designated port number. The port number is a configurable property in *resources/application.properties*

---
## Stop
In the *bin* directory is a bash file called *stopPowerEtlNotifier.sh*, executing this would retrieve the process id from *logs/procid* file and then kill the process. Execute *stopPowerEtlNotifier.sh* to stop PowerETL Notifier. 

---




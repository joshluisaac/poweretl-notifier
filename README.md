## PowerETL Notifier - some background
Notifies metrics, statistics and monitoring data exposing that through REST APIs for consumption by other tools and sub-systems.


---
## Sections
1. [Building PowerETL Notifier](#build)
2. [Starting PowerETL Notifier](#start)
3. [Stopping PowerETL Notifier](#Shutting)


---
## Requirements
PowerETL Notifier has the following requirements:

*   Apache maven 3.5.3+ (check with `mvn --version`)
*   Oracle JDK 1.8+ (check with `java -version`)


---
## Building PowerETL Notifier
Building the project will download the required dependencies, assemble the artefact into a build structure and generate a zip file for deployment. Execute the [build script](build.sh) to accomplish this. The build zip file can be found at the project root directory with a timestamp naming convention.  

The following log message is evidence of successful build generation
```
2018-11-06 10:10:45 Finished building, next step resumes in 2 secs
2018-11-06 10:10:47 Completed build assembly
2018-11-06 10:10:51 Created poweretl-notifier_20181106_101042.zip
2018-11-06 10:10:51 Clean up completed
```
The naming convention uses the format `poweretl-notifier_%Y%m%d_%H%M%S.zip`



---
## Starting PowerETL Notifier
Navigate into the `bin` directory and execute the [start script](bin/startPowerEtlNotifier.sh), this would start PowerETL Notifier and listening at the designated port number. The port number is a configurable property in [application properties](src/main/resources/application.properties)



---
## Shutting down PowerETL Notifier
In the `bin` directory is a bash file called [stop script](bin/stopPowerEtlNotifier.sh), executing this would retrieve the [process id](logs/procid) file and then kill the process. 


---
## API documentation

Documentation for APIs is provided here:

* [Javadoc](https://dummylink/apidocs/index.html) for notifier.

* [Swagger](https://dummylink/swagger/index.html) documentation for the notifier's REST interface.

---
## Recent changes

See [Recent changes](site/recent-changes.md) for recent changes to PowerETL Notifier, including any backward incompatible changes.



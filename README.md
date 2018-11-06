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

A successful startup is indicated by the log message as follows:
```
2018-11-06 19:40:16,715 INFO  Starting StartLogMonitor on xubuntuVostro with PID 12591 (/media/joshua/martian/kvworkspace/PowerETL/poweretl-notifier/target/classes started by joshua in /media/joshua/martian/kvworkspace/PowerETL/poweretl-notifier/bin)
2018-11-06 19:40:16,718 INFO  No active profile set, falling back to default profiles: default
2018-11-06 19:40:16,819 INFO  HV000001: Hibernate Validator 5.3.6.Final
2018-11-06 19:40:17,918 INFO  Logging initialized @1806ms to org.eclipse.jetty.util.log.Slf4jLog
2018-11-06 19:40:17,993 INFO  jetty-9.4.7.v20170914
2018-11-06 19:40:18,076 INFO  DefaultSessionIdManager workerName=node0
2018-11-06 19:40:18,076 INFO  No SessionScavenger set, using defaults
2018-11-06 19:40:18,079 INFO  Scavenging every 600000ms
2018-11-06 19:40:18,083 INFO  Initializing Spring embedded WebApplicationContext
2018-11-06 19:40:18,197 INFO  Started o.s.b.c.e.j.JettyEmbeddedWebAppContext@4cd1c1dc{/,[file:///tmp/jetty-docbase.4250930944911120981.8088/],AVAILABLE}
2018-11-06 19:40:18,198 INFO  Started @2086ms
2018-11-06 19:40:18,272 INFO  Initialized and constructed EmailConfigEntity Bean
2018-11-06 19:40:18,280 INFO  Initialized and constructed JavaMailSender Bean
2018-11-06 19:40:19,338 INFO  Initializing Spring FrameworkServlet 'dispatcherServlet'
2018-11-06 19:40:19,375 INFO  Started ServerConnector@47f08b81{HTTP/1.1,[http/1.1]}{0.0.0.0:8088}
2018-11-06 19:40:19,380 INFO  Started StartLogMonitor in 2.903 seconds (JVM running for 3.269)

```



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



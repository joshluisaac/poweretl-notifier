#!/bin/bash

################################################################################################################
## ONLY CHANGE THESE VARIABLES IN THIS SECTION
## THE REST OF THE SCRIPT SHOULD WORK FINE WITHOUT NEEDING ANY CHANGE FROM THE USER

## SET THE JAVA_HOME FIRST. THIS SHOULD BE AN ABSOLUTE PATH THE DIRECTORY WHERE JAVA FILES ARE INSTALLED
## THIS SHOULD NOT POINT TO THE 'bin' DIRECTORY
JAVA_HOME=/usr

## MINIMUM MEMORY TO BE ALLOCATED TO THE JVM, IN MEGABYTES
MIN_MEMORY=4096

## MAXIMUM MEMORY TO BE ALLOCATED TO THE JVM, IN MEGABYTES
MAX_MEMORY=8192

## LOG FILE NAME DATE FORMAT
## THIS IS AN ADVANCE SETTING, PLEASE DON'T PLAY WITH IT UNLESS YOU'RE AN EXPERT IN BASH 'date' COMMAND FORMAT
LOG_FILE_DATE_FORMAT=%Y%b%d%H%M%S

## IF YOU ARE GETTING PROBLEM WITH THE CLIENT COMPLAINING THE HOST/SERVER COULDN'T BE FOUND YOU SHOULD SET THIS
JAVA_RMI_HOST=10.8.2.75
#JAVA_RMI_HOST=10.202.1.12

## SET THIS IF YOU WANT TO ENABLE JMX
ENABLE_JMX=true
JMX_PORT=8000

## SET THIS IF YOU WANT TO ENABLE CUSTOM DIFFERENT GC
GC=+UseConcMarkSweepGC

## OTHER JAVA OPTIONS
## USE THIS SECTION TO SPECIFY ANY OTHER JAVA COMMAND LINE OPTIONS, SUCH AS -D
## IF THERE ARE NO OTHER OPTIONS, JUST COMMENT OUT THE LINE BELOW
#OTHER_OPTS=

## END OF CHANGEABLE SETTINGS
## PLEASE DON'T CHANGE ANYTHING AFTER THIS LINE
################################################################################################################

if [ -f $JAVA_HOME/bin/java ]; then
 JAVA=$JAVA_HOME/bin/java
else
 JAVA=`which java`
 if [ $? -ne 0 ]; then
  echo "Unable to find Java, plase ensure JAVA_HOME or path to Java is set in your environment!"
  exit 1
 fi
fi

echo $MIN_MEMORY | grep [^0-9] > /dev/null 2>&1
if [ "$?" -eq "0" ]; then
 echo "MIN_MEMORY value must be a valid number"
 exit 1
fi

echo $MAX_MEMORY | grep [^0-9] > /dev/null 2>&1
if [ "$?" -eq "0" ]; then
 echo "MAX_MEMORY value must be a valid number"
 exit 1
fi

CurDir=`dirname $0`
HomeDir=$CurDir/..
cd $CurDir


RESOURCES=$HomeDir/resources
LIB=$HomeDir/lib
LOGS=$HomeDir/logs


CLASSPATH=$RESOURCES
CLASSPATH=$CLASSPATH:$LIB/poweretl-notifier-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-dataaccess-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-services-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-utils-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:$LIB/etl-notification-0.0.1-SNAPSHOT.jar


CLASSPATH=$CLASSPATH:$LIB/activation-1.1.1.jar
CLASSPATH=$CLASSPATH:$LIB/apache-el-8.0.33.jar
CLASSPATH=$CLASSPATH:$LIB/asm-5.1.jar
CLASSPATH=$CLASSPATH:$LIB/asm-commons-5.1.jar
CLASSPATH=$CLASSPATH:$LIB/asm-tree-5.1.jar
CLASSPATH=$CLASSPATH:$LIB/bcprov-jdk15on-1.59.jar
CLASSPATH=$CLASSPATH:$LIB/classmate-1.3.4.jar
CLASSPATH=$CLASSPATH:$LIB/commons-email-1.4.jar
CLASSPATH=$CLASSPATH:$LIB/commons-logging-1.2.jar
CLASSPATH=$CLASSPATH:$LIB/commons-vfs2-2.1.jar

CLASSPATH=$CLASSPATH:$LIB/groovy-2.4.13.jar
CLASSPATH=$CLASSPATH:$LIB/gson-2.8.2.jar
CLASSPATH=$CLASSPATH:$LIB/hibernate-validator-5.3.6.Final.jar
CLASSPATH=$CLASSPATH:$LIB/ibatis-sqlmap-2.3.4.726.jar
CLASSPATH=$CLASSPATH:$LIB/jackson-annotations-2.8.0.jar
CLASSPATH=$CLASSPATH:$LIB/jackson-core-2.8.10.jar
CLASSPATH=$CLASSPATH:$LIB/jackson-databind-2.8.10.jar
CLASSPATH=$CLASSPATH:$LIB/javassist-3.21.0-GA.jar
CLASSPATH=$CLASSPATH:$LIB/javax-websocket-client-impl-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/javax-websocket-server-impl-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/javax.annotation-api-1.2.jar
CLASSPATH=$CLASSPATH:$LIB/javax.mail-1.5.6.jar
CLASSPATH=$CLASSPATH:$LIB/javax.servlet-api-3.1.0.jar
CLASSPATH=$CLASSPATH:$LIB/javax.websocket-api-1.0.jar
CLASSPATH=$CLASSPATH:$LIB/jboss-logging-3.3.1.Final.jar
CLASSPATH=$CLASSPATH:$LIB/jcl-over-slf4j-1.7.25.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-annotations-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-client-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-continuation-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-http-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-io-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-plus-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-security-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-server-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-servlet-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-servlets-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-util-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-webapp-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/jetty-xml-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/joda-time-2.9.9.jar
CLASSPATH=$CLASSPATH:$LIB/jsch-0.1.54.jar
CLASSPATH=$CLASSPATH:$LIB/jtds-1.3.1.jar
CLASSPATH=$CLASSPATH:$LIB/jul-to-slf4j-1.7.25.jar
CLASSPATH=$CLASSPATH:$LIB/log4j-over-slf4j-1.7.25.jar
CLASSPATH=$CLASSPATH:$LIB/logback-classic-1.1.11.jar
CLASSPATH=$CLASSPATH:$LIB/logback-core-1.1.11.jar
CLASSPATH=$CLASSPATH:$LIB/mybatis-3.4.1.jar
CLASSPATH=$CLASSPATH:$LIB/nekohtml-1.9.22.jar
CLASSPATH=$CLASSPATH:$LIB/ognl-3.0.8.jar
CLASSPATH=$CLASSPATH:$LIB/postgresql-9.4.1212.jre7.jar
CLASSPATH=$CLASSPATH:$LIB/slf4j-api-1.7.25.jar
CLASSPATH=$CLASSPATH:$LIB/snakeyaml-1.17.jar
CLASSPATH=$CLASSPATH:$LIB/spring-aop-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-beans-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-autoconfigure-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-devtools-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-starter-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-starter-jetty-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-starter-logging-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-starter-mail-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-starter-thymeleaf-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-boot-starter-web-1.5.9.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-context-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-context-support-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-core-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-expression-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-web-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/spring-webmvc-4.3.13.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/thymeleaf-2.1.6.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/thymeleaf-layout-dialect-1.4.0.jar
CLASSPATH=$CLASSPATH:$LIB/thymeleaf-spring4-2.1.6.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/unbescape-1.1.0.RELEASE.jar
CLASSPATH=$CLASSPATH:$LIB/validation-api-1.1.0.Final.jar
CLASSPATH=$CLASSPATH:$LIB/websocket-api-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/websocket-client-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/websocket-common-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/websocket-server-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/websocket-servlet-9.4.7.v20170914.jar
CLASSPATH=$CLASSPATH:$LIB/xercesImpl-2.11.0.jar
CLASSPATH=$CLASSPATH:$LIB/xml-apis-1.4.01.jar


LOG_FILE=$LOGS/PowerEtlNotifier-`date +"$LOG_FILE_DATE_FORMAT"`.log

JAVA_OPTS="-classpath $CLASSPATH"
JAVA_OPTS=$JAVA_OPTS" -Xms"$MIN_MEMORY"m"
JAVA_OPTS=$JAVA_OPTS" -Xmx"$MAX_MEMORY"m"
JAVA_OPTS=$JAVA_OPTS" -Djava.awt.headless=true"
JAVA_OPTS=$JAVA_OPTS" -Djava.io.tmpdir="$LOGS/tmp

if [ -n "$JAVA_RMI_HOST" ]; then
 JAVA_OPTS=$JAVA_OPTS" -Djava.rmi.server.hostname="$JAVA_RMI_HOST
fi

if [ "$ENABLE_JMX" == "true" -o "$ENABLE_JMX" == "TRUE" ]; then
  if [ -n "$JMX_PORT" ]; then
   echo $JMX_PORT | grep [^0-9] > /dev/null 2>&1
	 if [ "$?" -eq "0" ]; then
 		echo "JMX_PORT value must be a valid number"
 		exit 1
 	 fi
 	else
 	 echo "JMX port not provided, defaulting to 8000"
 	 JMX_PORT=8000
	fi
	JAVA_OPTS=$JAVA_OPTS" -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
fi

if [ -n "$GC" ]; then
 JAVA_OPTS=$JAVA_OPTS" -XX:PermSize=512m -XX:MaxPermSize=1024m -XX:+CMSClassUnloadingEnabled -XX:+CMSPermGenSweepingEnabled -XX:"$GC
fi

if [ -n "$OTHER_OPTS" ]; then
 JAVA_OPTS=$JAVA_OPTS" $OTHER_OPTS"
fi

nohup $JAVA $JAVA_OPTS -Xloggc:$LOGS/gc.log -XX:+PrintGCDetails com.powerapps.monitor.StartLogMonitor 1>$LOG_FILE 2>&1 &

echo $! > $LOGS/procid
echo $LOG_FILE > $LOGS/logname
chmod 770 $LOGS/logname
chmod 770 $LOGS/procid
chmod 770 $LOG_FILE

loopCnt=0
while [ : ]
do
loopCnt=`expr $loopCnt + 1`
if [ -f $LOGS/Application.log ]; then
 chmod 770 $LOGS/Application.log
 break
fi
if [ $loopCnt -eq 10000 ]; then
 echo "Unable to find Application.log, PowerNotificator may not be started succesfully, please do a manual check!"
 break
 exit 1
fi
done

nohup $HomeDir/bin/mstat.sh 1>$LOGS/mstat.log 2>&1 &

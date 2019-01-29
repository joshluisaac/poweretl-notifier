#!/bin/bash

WAIT_PERIOD=2
mvn_log_file=mvn.log

rm -rf $mvn_log_file
right_now=$(date +"%Y%m%d")
timestamp=$(date +"%Y%m%d_%H%M%S")
build_name="poweretl-notifier_"$timestamp

mvn_clean_install(){
mvn clean install -Dmaven.test.skip dependency:copy-dependencies > $mvn_log_file
}

#check_build_compilation
check_build_compilation() {
grep_result=`grep -P "BUILD SUCCESS" $mvn_log_file | wc -l`
if [[ $grep_result -ge 1 ]]
then
echo `date '+%Y-%m-%d %H:%M:%S'` "Finished building, next step resumes in $WAIT_PERIOD secs"
sleep $WAIT_PERIOD
else
sleep $WAIT_PERIOD
echo `date '+%Y-%m-%d %H:%M:%S'` "Build is still in progress"
check_build_compilation
fi
}


assemble_build(){
rm -rf poweretl-notifier_*
mkdir $build_name
cp target/poweretl-notifier-0.0.1-SNAPSHOT.jar lib
cp -r lib $build_name/lib
cp -r bin $build_name/bin
cp -r logs $build_name/logs
cp -r out $build_name/out
cp -r src/main/resources $build_name/resources
echo `date '+%Y-%m-%d %H:%M:%S'` "Completed build assembly"
}


zip_build(){
zip -r $build_name.zip $build_name > zip.log
echo `date '+%Y-%m-%d %H:%M:%S'` "Created $build_name.zip"
}


clean_up(){
rm -rf $build_name
echo `date '+%Y-%m-%d %H:%M:%S'` "Clean up completed"
}





build(){
rm -rf "lib/*.jar"
mvn_clean_install
check_build_compilation
assemble_build
zip_build
clean_up
}



build

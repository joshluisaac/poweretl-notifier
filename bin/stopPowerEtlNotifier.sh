#!/bin/bash

CurDir=`dirname $0`
HomeDir=$CurDir/..

LOGS=$HomeDir/logs

echo "Attempting to stop PowerEtl-Notifier"

procid=`cat $LOGS/procid`

ps -p $procid > /dev/null
if [ $? -ne 0 ]; then
  echo "PowerEtl-Notifier is not running.. can't stop something which is not running!"
else
  kill -9 $procid > /dev/null
  if [ $? -eq 0 ]; then
    echo "PowerEtl-Notifier is stopped succesfully!"
  else
    echo "Unable to stop PowerEtl-Notifier, if you're sure it's running, please stop manually!"
  fi
fi

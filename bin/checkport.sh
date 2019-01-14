#!/bin/bash


procid=`cat $LOGS/procid`


netstat -plnt | grep $procid

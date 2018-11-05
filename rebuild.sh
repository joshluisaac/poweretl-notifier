#!/bin/bash
mvn clean install -Dmaven.test.skip dependency:copy-dependencies

#mvn dependency:copy-dependencies@copy-dependencies

#mvn exec:java


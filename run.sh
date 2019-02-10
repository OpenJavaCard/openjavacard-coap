#!/bin/sh
home=`dirname $0`
java -Dorg.slf4j.simpleLogger.defaultLogLevel=TRACE -jar $home/californium/build/jar/openjavacard-coap-californium-0.1-SNAPSHOT-fat.jar "$@"

#!/bin/sh
home=`dirname $0`
java -Dorg.slf4j.simpleLogger.defaultLogLevel=TRACE -jar $home/client/build/jar/openjavacard-coap-client-0.1-SNAPSHOT-fat.jar "$@"

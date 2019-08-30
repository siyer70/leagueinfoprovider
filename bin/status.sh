#!/bin/bash
VERSION=${1:-"1.0"}
API_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "java " | grep -i "infoserver-$VERSION.jar" | tr -s " " | cut -d " " -f 2`
if [ -z "$API_SERVER_PID" ]
then
      echo "API Server not running"
else
        echo "API Server running with PID: $API_SERVER_PID"
fi
WEB_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "serve" | grep -i "8400"| tr -s " " | cut -d " " -f 2`
if [ -z "$WEB_SERVER_PID" ]
then
      echo "Web Server not running"
else
        echo "Web Server running with PID: $WEB_SERVER_PID"
fi


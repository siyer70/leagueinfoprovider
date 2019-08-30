#!/bin/bash
VERSION=${1:-"1.0"}
API_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "java " | grep -i "infoserver-$VERSION.jar" | tr -s " " | cut -d " " -f 2`
if [ -z "$API_SERVER_PID" ]
then
      echo "API Server not running"
else
        kill -9 $API_SERVER_PID
        echo "API Server is stopped"
fi
WEB_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "serve" | grep -i "build"| tr -s " " | cut -d " " -f 2`
if [ -z "$WEB_SERVER_PID" ]
then
      echo "Web Server not running"
else
        kill -9 $WEB_SERVER_PID
        echo "Web Server is stopped"
fi


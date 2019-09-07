#!/bin/bash
ENVIRONMENT=${1:-"PROD"}
VERSION=${2:-"1.0"}
ENVIRONMENT=`echo $ENVIRONMENT | tr a-z A-Z`
API_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "java " | grep -i "$ENVIRONMENT" | grep -i "infoserver-$VERSION.jar" | tr -s " " | cut -d " " -f 2`
if [ -z "$API_SERVER_PID" ]; then
      echo "API Server not running"
else
        echo "API Server running with PID: $API_SERVER_PID"
fi

if [ "$ENVIRONMENT" == "PROD" ]; then
	WEB_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "serve" | grep -i "8400"| tr -s " " | cut -d " " -f 2`
	if [ -z "$WEB_SERVER_PID" ]
	then
	      echo "Web Server not running"
	else
	        echo "Web Server running with PID: $WEB_SERVER_PID"
	fi
fi

if [ "$ENVIRONMENT" == "DEV" ]; then
	WEB_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "react-scripts" | grep -i "start.js"| tr -s " " | cut -d " " -f 2`
	if [ -z "$WEB_SERVER_PID" ]
	then
	      echo "Web Server not running"
	else
	        echo "Web Server running with PID: $WEB_SERVER_PID"
	fi
fi

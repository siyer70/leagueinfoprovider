#!/bin/bash
echo "Starting API Server.."
ENVIRONMENT=${1:-"PROD"}
VERSION=${2:-"1.0"}
ENVIRONMENT=`echo $ENVIRONMENT | tr a-z A-Z`
API_SERVER_PORT=8500
if [ "$ENVIRONMENT" == "DEV" ]; then
	API_SERVER_PORT=8080
fi
mkdir -p logs
nohup java -Dserver.port=$API_SERVER_PORT -jar releases/$ENVIRONMENT/infoserver-$VERSION/infoserver-$VERSION.jar > logs/apiserver.log 2>&1 &
echo "waiting for API Server to come up"
sleep 10
API_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "java " | grep -i "$ENVIRONMENT" | grep -i "infoserver-$VERSION.jar" | tr -s " " | cut -d " " -f 2`
if [ -z "$API_SERVER_PID" ]; then
      echo "API Server not running"
else
        echo "API Server running with PID: $API_SERVER_PID"
fi

echo "Starting Web Server.."
if [ "$ENVIRONMENT" == "PROD" ]; then
	nohup serve -s releases/$ENVIRONMENT/infoserver-$VERSION/build -l 8400 > logs/ui.log 2>&1 &
	sleep 5
	WEB_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "serve" | grep -i "8400"| tr -s " " | cut -d " " -f 2`
	if [ -z "$WEB_SERVER_PID" ]
	then
	      echo "Web Server not running"
	else
	      echo "Web Server running with PID: $WEB_SERVER_PID"
	      echo "Happy browsing!"
	fi	
fi

if [ "$ENVIRONMENT" == "DEV" ]; then
	nohup sh -c 'cd league-info-app/ && npm start' > logs/ui.log 2>&1 &
	sleep 2
	WEB_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "react-scripts" | grep -i "start.js"| tr -s " " | cut -d " " -f 2`
	if [ -z "$WEB_SERVER_PID" ]
	then
	      echo "Web Server not running"
	else
	      echo "Web Server running with PID: $WEB_SERVER_PID"
	      echo "Happy browsing!"
	fi	
fi

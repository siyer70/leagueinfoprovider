#!/bin/bash
SELENIUM_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "java " | grep -i "selenium-server-standalone" | tr -s " " | cut -d " " -f 2`
if [ -z "$SELENIUM_SERVER_PID" ]
then
      echo "Selenium Server not running"
else
		echo "Stopping Server with PID: $SELENIUM_SERVER_PID.."
        kill -9 $SELENIUM_SERVER_PID
        echo "Selenium Server is stopped"
fi

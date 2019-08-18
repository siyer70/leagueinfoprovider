#!/bin/bash
PLATFORM=$1
echo "Starting Selenium Server.."

java -jar /tmp/selenium-server/selenium-server-standalone-2.48.2.jar -browser "browserName=chrome, version=76.0" -Dwebdriver.chrome.driver=/tmp/selenium-server/$PLATFORM/chromedriver > /tmp/selenium-server.log 2>&1 &
echo "waiting for Selenium Server to come up"
sleep 3
SELENIUM_SERVER_PID=`ps -ef | grep -v -e 'grep ' | grep -i "java " | grep -i "selenium-server-standalone" | tr -s " " | cut -d " " -f 2`
if [ -z "$SELENIUM_SERVER_PID" ]
then
      echo "Selenium Server not running"
      exit -1
else
        echo "Selenium Server is running now - PID: $SELENIUM_SERVER_PID"
fi
exit 0
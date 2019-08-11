#!/bin/bash
echo "Starting API Server.."
VERSION=${1:-"1.0"}
nohup java -Dserver.port=8500 -jar release/PROD/infoserver-$VERSION/infoserver-$VERSION.jar > logs/apiserver.log 2>&1 &
echo "waiting for API Server to come up"
sleep 10
echo "Starting Web Server.."
nohup serve -s release/PROD/infoserver-$VERSION/build -l 8080 > logs/ui.log 2>&1 &
sleep 5
echo "Happy browsing!"

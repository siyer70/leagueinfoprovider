#!/bin/bash
echo "Starting API Server.."
VERSION=${1:-"1.0"}
nohup java -Dserver.port=8500 -jar releases/PROD/infoserver-$VERSION/infoserver-$VERSION.jar > logs/apiserver.log 2>&1 &
echo "waiting for API Server to come up"
sleep 10
echo "Starting Web Server.."
nohup serve -s releases/PROD/infoserver-$VERSION/build -l 8400 > logs/ui.log 2>&1 &
sleep 5
echo "Happy browsing!"

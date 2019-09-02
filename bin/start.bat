@Echo Off
set ENVIRONMENT=DEV
set VERSION=1.0
set homedir=%~dp0..
echo %homedir%
IF NOT EXIST "%homedir%\logs" (mkdir %homedir%\logs) 
cd %homedir%
echo "Starting API Server.."
start java -Dserver.port=8500 -jar releases\%ENVIRONMENT%\infoserver-%VERSION%\infoserver-%VERSION%.jar > logs\apiserver.log 2>&1
timeout /t 20
echo "Starting Web Server.."
cd league-info-app\
start npm start > ..\logs/ui.log 2>&1
cd ..

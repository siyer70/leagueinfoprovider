#!/bin/bash
REMOTE_HOST=${1:-"13.58.209.5"}

ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem 'mkdir -p infoserver'
ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem 'mkdir -p infoserver/bin'
ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem 'mkdir -p infoserver/logs'
ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem 'mkdir -p infoserver/backup'

scp -v -o StrictHostKeyChecking=no  -i ~/Documents/PE/Jenkins/jenkins.pem bin/start.sh ubuntu@$REMOTE_HOST:/home/ubuntu/infoserver/bin/

scp -v -o StrictHostKeyChecking=no  -i ~/Documents/PE/Jenkins/jenkins.pem bin/status.sh ubuntu@$REMOTE_HOST:/home/ubuntu/infoserver/bin/

scp -v -o StrictHostKeyChecking=no  -i ~/Documents/PE/Jenkins/jenkins.pem bin/stop.sh ubuntu@$REMOTE_HOST:/home/ubuntu/infoserver/bin/

scp -v -o StrictHostKeyChecking=no  -i ~/Documents/PE/Jenkins/jenkins.pem bin/backuplastrelease.sh ubuntu@$REMOTE_HOST:/home/ubuntu/infoserver/bin/

ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem 'cd /home/ubuntu/infoserver && bin/backuplastrelease.sh'

scp -v -o StrictHostKeyChecking=no  -i ~/Documents/PE/Jenkins/jenkins.pem release.tar.gz ubuntu@$REMOTE_HOST:/home/ubuntu/infoserver/
ret=$?
if [ $ret -ne 0 ]; then
	echo "File transfer failed"
	exit $ret
fi

ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem '/home/ubuntu/infoserver/bin/stop.sh'
ret=$?
if [ $ret -ne 0 ]; then
	echo "Could not stop the running instance"
	exit $ret
fi

ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem 'cd /home/ubuntu/infoserver && ls -ltr && tar -xvf release.tar.gz'
ret=$?
if [ $ret -ne 0 ]; then
	echo "Could not extract the zip file"
	exit $ret
fi

ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem '\. /home/ubuntu/.nvm/nvm.sh && cd /home/ubuntu/infoserver && echo $PATH && bin/start.sh'
ret=$?
if [ $ret -ne 0 ]; then
	echo "Could not start the new version of the application"
	exit $ret
fi

ssh ubuntu@$REMOTE_HOST -i ~/Documents/PE/Jenkins/jenkins.pem '/home/ubuntu/infoserver/bin/status.sh'

exit $ret
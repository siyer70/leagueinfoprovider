A Service to query team standing in Football League

Steps for local setup are provided below:

Pre-requisites
	
	Install Java 8
	Install node.js (v10.16.1) and npm (v6.9.0)
	Install serve globally (npm install -g serve)
	Install JMeter (Optional)
	
Setup Steps
1.	Check out the code from github (git clone https://github.com/siyer70/leagueinfoserver)
2.	Build the code with the command on the root directory of checked out folder: “jenkins/scripts/build.sh PROD 1.0”
3.	Start the services:  bin/start.sh 
4.	You can also stop the services by using: bin/stop.sh
5.	Check the status of the service: bin/status.sh
	
	Status should be running
	
	UI code runs in 8400 port of localhost
	
	Service runs in 8500 port of localhost
	
	So, you can access the app using url: http://localhost:8400/ 
	
	And access the API service using the swagger url: http://localhost:8500/v1/swagger-ui.html 
	

Pre-requisites

	Install Java 8
	Install node.js (v10.16.1) and npm (v6.9.0)
	Install serve globally (npm install -g serve)
	Install JMeter (Optional)
	
Setup Steps
1.	Check out the code from github (git clone https://github.com/siyer70/leagueinfoserver)
		
		Once checked-out, Change directory to this folder
		Note: All under-mentioned commands are to be executed from this root directory of the checked-out folder only
		
2.	Build the code with the under-mentioned command
		
		Command: “jenkins/scripts/build.sh DEV 1.0” (use “jenkins/scripts/build.sh PROD 1.0” for PROD)
		Use Gitbash for Windows to run the above
		
3.	Start the services:  bin/start.sh (MAC / Linux) or bin\start.bat (Windows)
		
		You can stop the services by using: bin/stop.sh (MAC / Linux) - In Windows – simply close the spawned windows
	
4.	Check the status of the service: bin/status.sh (MAC / Linux) - In Windows, status is visible in the spawned windows
		
		Status should be running
		UI code runs in 3000 port of localhost (npm start default) – In Prod, port 8400 is used in EC2 host
		Service runs in 8500 port of localhost – In Prod, same port is used in EC2 host
		So, you can access the app using url: 
		
			Local: http://localhost:3000/ 
			Prod:  http://13.58.209.5:8080 
				
				In Prod, since 8080 is the only port opened, Apache as reverse proxy / gateway is configured in this port to manage the requests for both Web Server & API Server (see the architecture page for more details)
				And access the API service using the swagger url:
				
				Local:  http://localhost:8500/v1/swagger-ui.html 
				Prod: http://13.58.209.5:8080/v1/swagger-ui.html

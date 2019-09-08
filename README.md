This section only covers how to setup and run the solution locally

For a deep dive on the architecture, tech stack, implementation and testing details, please find the "LeagueServerDocumentation.pdf" in the docs folder.

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
		In Windows - Use gitbash to run the above
		
3.	Start the services:  

		In MAC / Ubuntu - use the following command:
		bin/start.sh [PROD | DEV] 
		for example: 
			bin/start.sh DEV
			or
			bin/start.sh PROD
			(default is PROD - if you don't specify any param, it will start in PROD mode)
		
		In Windows - use the following command:
		bin\start.bat
		(It will always start in DEV mode)

4.	To stop the services:
	
		In MAC / Ubuntu - use the following command
		bin/stop.sh [PROD | DEV] 

		In Windows – simply close the spawned windows
	
4.	Check the status of the service: 

		In MAC / Ubuntu - use the following command
		bin/status.sh [PROD | DEV] 

		In Windows, status is visible in the spawned windows

Other Notes
		
		a. UI Code (applicable for MAC / Linux / Windows)
			- In dev mode, UI code runs in port 3000 of localhost (npm start default) 
			– In Prod, port 8400 is used in EC2 ubuntu host

		b. Service (applicable for MAC / Linux / Windows)
			- In dev mode, it runs in 8080 port of localhost (default port of tomcat) 
			– In Prod, 8500 port is used in EC2 ubuntu host
		
		So, you can access the app using url: 
		
			Local: http://localhost:3000/ 
			Prod:  http://13.58.209.5:8080 
				
		(In Prod, since 8080 is the only port opened, Apache as reverse proxy / gateway 
		is configured in this port to manage the requests for both Web Server & API Server 
		- see the architecture page for more details)
		
		And access the API service using the swagger url:
				
				Local:  http://localhost:8080/v1/swagger-ui.html 
				Prod: http://13.58.209.5:8080/v1/swagger-ui.html

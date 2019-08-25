#!/bin/bash
/Users/chasrini/apache-jmeter-5.1.1/bin/jmeter -n -t jmeter-tests/InfoserverAPITest.jmx -l jmeter-tests/logs/jmeter-text-log.jtl -j jmeter-tests/logs/jmeter-run.log

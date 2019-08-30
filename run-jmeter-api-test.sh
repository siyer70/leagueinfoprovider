#!/bin/bash
mkdir -p jmeter-tests/logs
jmeter -n -t jmeter-tests/InfoserverAPITest.jmx -l jmeter-tests/logs/jmeter-text-log.jtl -j jmeter-tests/logs/jmeter-run.log
